package ru.bmstu.www.player.impl;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Observable;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import ru.bmstu.www.equalizer.Equalizer;
import ru.bmstu.www.fft.FFT;
import ru.bmstu.www.input.AudioFileFormat;
import ru.bmstu.www.input.ReadMusicFile;
import ru.bmstu.www.player.IAudioPlayer;

public class AudioPlayer extends Observable implements IAudioPlayer {
	
	private SourceDataLine sourceDataLine;
	private AudioInputStream ais;
	private byte[] buff;
	private final int BUFF_SIZE = 1024;
	private short[] sampleBuff;

	private Equalizer equalizer;

	private volatile boolean paused;
	private volatile boolean running;
	private final Object pauseLock = new Object();

	private AudioFormat format;
	private FFT fastFourierInput;
	private FFT fastFourierOutput;
	private boolean isFFTready = false;

	public AudioPlayer(File musicFile)
			throws UnsupportedAudioFileException, IOException, InterruptedException, LineUnavailableException {
		ReadMusicFile readFile = new ReadMusicFile(musicFile);

		this.sourceDataLine = readFile.getSourceDataLine();
		this.ais = readFile.getAudioInputStream();
		this.buff = new byte[this.BUFF_SIZE];
		this.sampleBuff = new short[BUFF_SIZE / 2];
		this.equalizer = new Equalizer(BUFF_SIZE / 2);
		AudioFileFormat aff = new AudioFileFormat();
		format = new AudioFormat((float) aff.getSampleRate(), aff.getBits(), aff.getChannels(), aff.isSigned(),
				aff.isBigEndian());
		this.fastFourierInput = new FFT();
		this.fastFourierOutput = new FFT();
	}

	public boolean isPause() {
		return this.paused;
	}

	private short[] ByteArrayToSamplesArray() {
		for (int i = 0, j = 0; i < this.buff.length - 1; i += 2, j++) {
			this.sampleBuff[j] = (short) ((ByteBuffer.wrap(this.buff, i, 2).order(java.nio.ByteOrder.LITTLE_ENDIAN)
					.getShort()));
		}
		return this.sampleBuff;
	}

	private byte[] SampleArrayByteArray() {
		for (int i = 0, j = 0; i < this.sampleBuff.length && j < this.buff.length; i++, j += 2) {
			this.buff[j] = (byte) (this.sampleBuff[i]);
			this.buff[j + 1] = (byte) (this.sampleBuff[i] >>> 8);
		}

		return buff;

	}

	public Equalizer getEqualizer() {
		return this.equalizer;
	}

	public double[] getFTvlOutput() {
		return this.fastFourierOutput.getFTvl();
	}

	public double[] getFTvlInput() {
		return this.fastFourierInput.getFTvl();
	}

	public boolean isFftReady() {
		return this.isFFTready;
	}

	@Override
	public void run() {
		try {
			this.sourceDataLine.open(this.format);
			this.sourceDataLine.start();
			this.paused = false;
			this.running = true;
			while ((this.ais.read(this.buff, 0, this.BUFF_SIZE) != -1) && running) {

				if (!this.sync())
					break;

				this.sampleBuff = this.ByteArrayToSamplesArray();

				isFFTready = false;
				this.fastFourierInput.fft(this.sampleBuff);

				equalizer.setInputSignal(this.sampleBuff);
				this.equalizer.equalization();
				this.sampleBuff = equalizer.getOutputSignal();

				this.fastFourierOutput.fft(this.sampleBuff);

				isFFTready = true;

				setChanged();
				notifyObservers();

				this.buff = this.SampleArrayByteArray();
				sourceDataLine.write(this.buff, 0, this.buff.length);
			}
			this.isFFTready = false;
			this.sourceDataLine.drain();
			this.sourceDataLine.close();
		} catch (Exception e) {
			if (running)
				e.printStackTrace();
		}

	}

	@Override
	public void pause() {
		// you may want to throw an IllegalStateException if !running
		paused = true;
	}

	@Override
	public void resume() {
		synchronized (pauseLock) {
			paused = false;
			pauseLock.notifyAll(); // Unblocks thread
		}
	}

	@Override
	public void stop() {
		running = false;

		if (this.ais != null)
			try {
				this.ais.close();
				this.equalizer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (this.sourceDataLine != null)
			this.sourceDataLine.close();

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
					pauseLock.wait(); // will cause this Thread to block until
										// another thread calls
										// pauseLock.notifyAll()
										// Note that calling wait() will
										// relinquish the synchronized lock that
										// this
										// thread holds on pauseLock so another
										// thread
										// can acquire the lock to call
										// notifyAll()
										// (link with explanation below this
										// code)
				} catch (InterruptedException ex) {
					return false;
				}
				if (!running) { // running might have changed since we paused
					return false;
				}
			}
		}
		return true;
	}

}
