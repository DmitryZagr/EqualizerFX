package ru.bmstu.www.player.impl;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import ru.bmstu.www.equalizer.Equalizer;
import ru.bmstu.www.fft.FFT;
import ru.bmstu.www.filter.coefs.FilterInfo;
import ru.bmstu.www.input.AudioFileFormat;
import ru.bmstu.www.input.ReadMusicFile;
import ru.bmstu.www.player.IAudioPlayer;
import ru.bmstu.www.util.EqualizerMessages;

public class AudioPlayer extends Observable implements IAudioPlayer {

	private static Logger log = Logger.getLogger(AudioPlayer.class.getName());
	private static String TAG = AudioPlayer.class.getName() + ": ";

	private Equalizer equalizer;

	private Player player = new Player();

	private Thread playThread;

	private final int BUFF_SIZE = 512;

	private FFT fastFourierFromUnmodifiedSignal;
	private FFT fastFourierFromModifiedSignal;
	private boolean isFFTready = false;

	public AudioPlayer() {

		Equalizer.EqualizerBuilder equalizerBuilder = new Equalizer.EqualizerBuilder();
		equalizer = equalizerBuilder.countOfBands(FilterInfo.CoefsOfBands.length).lenghtOfInputSignal(BUFF_SIZE / 2)
				.build();
		this.addObserver(equalizer);

		this.fastFourierFromUnmodifiedSignal = new FFT();
		this.fastFourierFromModifiedSignal = new FFT();

		log.log(Level.INFO, TAG + "AudioPlayer created");
	}

	public boolean isPause() {
		return player.paused;
	}

	public Equalizer getEqualizer() {
		return this.equalizer;
	}

	public double[] getFTvlOutput() {
		return this.fastFourierFromModifiedSignal.getFTvl();
	}

	public double[] getFTvlInput() {
		return this.fastFourierFromUnmodifiedSignal.getFTvl();
	}

	public boolean isFftReady() {
		return this.isFFTready;
	}

	@Override
	public void setMusicFile(File musicFile) {
		setChanged();
		notifyObservers(EqualizerMessages.UPD_GAIN);
		player.setMusicFile(musicFile);
		log.log(Level.INFO, TAG + "setMusicFile");
	}

	@Override
	public void play() {
		if (this.playThread != null)
			return;
		this.playThread = new Thread(this.player);
		playThread.start();

	}

	@Override
	public void pause() {
		player.paused = true;
	}

	@Override
	public void resume() {
		player.resume();
	}

	@Override
	public void stop() {
		player.stop();
	}

	private class Player implements Runnable {

		private SourceDataLine sourceDataLine;
		private AudioInputStream ais;
		private AudioFormat format;

		private volatile boolean paused;
		private volatile boolean running;
		private final Object pauseLock = new Object();

		private byte[] buff = new byte[BUFF_SIZE];
		private short[] sampleBuff = new short[BUFF_SIZE / 2];

		Player() {
			AudioFileFormat aff = new AudioFileFormat();
			format = new AudioFormat((float) aff.getSampleRate(), aff.getBits(), aff.getChannels(), aff.isSigned(),
					aff.isBigEndian());
		}

		@Override
		public void run() {
			try {

				this.paused = false;
				running = true;
				while (sync() && (ais.read(buff, 0, BUFF_SIZE) != -1) && running) {

					sampleBuff = byteArrayToSamplesArray();

					isFFTready = false;
					fastFourierFromUnmodifiedSignal.fft(sampleBuff);

					equalizer.setInputSignal(sampleBuff);
					equalizer.equalization();
					sampleBuff = equalizer.getOutputSignal();

					fastFourierFromModifiedSignal.fft(sampleBuff);

					isFFTready = true;

					setChanged();
					notifyObservers();

					buff = sampleArrayToByteArray();
					sourceDataLine.write(buff, 0, buff.length);
				}
				isFFTready = false;
				sourceDataLine.drain();
				sourceDataLine.close();
				ais.close();
				ais = null;
			} catch (Exception e) {
				if (running)
					e.printStackTrace();
			}

			playThread = null;

		}

		public void pause() {
			// you may want to throw an IllegalStateException if !running
			paused = true;
		}

		public void resume() {
			synchronized (pauseLock) {
				paused = false;
				pauseLock.notifyAll(); // Unblocks thread
			}
		}

		public void stop() {
			running = false;

			if (ais != null)
				try {
					ais.close();
					equalizer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (sourceDataLine != null)
				sourceDataLine.close();

			// you might also want to do this:
			Thread.currentThread().interrupt();
		}

		private boolean sync() {
			synchronized (pauseLock) {
				if (!running) { // may have changed while waiting to
								// synchronize on pauseLock
					return false;
				}
				if (paused) {
					try {
						pauseLock.wait(); // will cause this Thread to block
											// until
											// another thread calls
											// pauseLock.notifyAll()
											// Note that calling wait() will
											// relinquish the synchronized lock
											// that
											// this
											// thread holds on pauseLock so
											// another
											// thread
											// can acquire the lock to call
											// notifyAll()
											// (link with explanation below this
											// code)
					} catch (InterruptedException ex) {
						return false;
					}
					if (!running) { // running might have changed since we
									// paused
						return false;
					}
				}
			}
			return true;
		}

		private short[] byteArrayToSamplesArray() {
			for (int i = 0, j = 0; i < buff.length - 1; i += 2, j++) {
				sampleBuff[j] = (short) ((ByteBuffer.wrap(buff, i, 2).order(java.nio.ByteOrder.LITTLE_ENDIAN)
						.getShort()));
			}
			return sampleBuff;
		}

		private byte[] sampleArrayToByteArray() {
			for (int i = 0, j = 0; i < sampleBuff.length && j < buff.length; i++, j += 2) {
				buff[j] = (byte) (sampleBuff[i]);
				buff[j + 1] = (byte) (sampleBuff[i] >>> 8);
			}

			return buff;
		}

		private void setMusicFile(File musicFile) {
			if (ais != null) {
				this.pause();
				closeAIS();
			}

			ReadMusicFile readFile = null;

			try {
				readFile = new ReadMusicFile(musicFile);
			} catch (UnsupportedAudioFileException | IOException | InterruptedException | LineUnavailableException e) {
				e.printStackTrace();
				System.exit(-1);
			}

			this.sourceDataLine = readFile.getSourceDataLine();
			try {
				sourceDataLine.open(format);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			sourceDataLine.start();

			this.ais = readFile.getAudioInputStream();
			this.resume();
		}

		private void closeAIS() {
			try {
				this.ais.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}

	}

}
