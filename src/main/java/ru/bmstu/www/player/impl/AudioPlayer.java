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

import ru.bmstu.www.effects.impl.Delay;
import ru.bmstu.www.effects.impl.Overdrive;
import ru.bmstu.www.equalizer.Equalizer;
import ru.bmstu.www.fft.FFT;
import ru.bmstu.www.filter.coefs.FilterInfo;
import ru.bmstu.www.input.AudioFileFormat;
import ru.bmstu.www.input.ReadMusicFile;
import ru.bmstu.www.player.IAudioPlayer;

public class AudioPlayer extends Observable implements IAudioPlayer {
	private double volume;
	private SourceDataLine sourceDataLine;
	private AudioInputStream ais;
	private byte[] buff;
	private final int BUFF_SIZE = 1024 + ru.bmstu.www.filter.coefs.FilterInfo.COUNT_OF_COEFS;

	private short[] sampleBuff;

	private Delay delay;
	private boolean isDelay;

	private Overdrive overdrive;
	private double overdriveCoef;
	private boolean isOverdrive;

	private Equalizer equalizer;

	private volatile boolean paused;
	private volatile boolean running;
	private final Object pauseLock = new Object();

	private AudioFormat format;
	private FFT fastFourierInput;
	private FFT fastFourierOutput;
	private boolean FFTready = false;
	private short[] prevSignal;

	public AudioPlayer(File musicFile)
			throws UnsupportedAudioFileException, IOException, InterruptedException, LineUnavailableException {
		ReadMusicFile readFile = new ReadMusicFile(musicFile);

		this.sourceDataLine = readFile.getSourceDataLine();
		this.ais = readFile.getAudioInputStream();
		this.buff = new byte[this.BUFF_SIZE];
		this.sampleBuff = new short[BUFF_SIZE / 2];
		this.delay = new Delay();
		this.overdrive = new Overdrive();
		this.isDelay = false;
		this.isOverdrive = false;
		this.overdriveCoef = 1.0;
		this.equalizer = new Equalizer(BUFF_SIZE / 2);
		AudioFileFormat aff = new AudioFileFormat();
		format = new AudioFormat((float) aff.getSampleRate(), aff.getBits(), aff.getChannels(), aff.isSigned(),
				aff.isBigEndian());
		this.volume = 0.3;
		this.fastFourierInput = new FFT();
		this.fastFourierOutput = new FFT();
		this.prevSignal = new short[ru.bmstu.www.filter.coefs.FilterInfo.COUNT_OF_COEFS - 1];
	}

	private void delay(short[] inputSamples) {
		this.delay.setInputSampleStream(inputSamples);
		this.delay.createEffect();
	}

	public boolean delayIsActive() {
		return this.isDelay;
	}

	public void setDelay(boolean b) {
		this.isDelay = b;
	}

	public void setDelayCoef(double coef) {
		this.delay.setDelayCoef(coef);
	}

	private void overdrive(short[] inputSamples) {
		this.overdrive.setOverdriveCoef(this.overdriveCoef);
		this.overdrive.setInputSampleStream(inputSamples);
		this.overdrive.createEffect();
	}

	public boolean overdriveIsActive() {
		return this.isOverdrive;
	}

	public void setOverdrive(boolean b) {
		this.isOverdrive = b;
	}

	public void setOverdriveCoef(double c) {
		this.overdriveCoef = c;
	}

	public void setPause(boolean pause) {
		this.paused = pause;
	}

	public boolean getPause() {
		return this.paused;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getVolume() {
		return this.volume;
	}

	public short[] getSamples() {
		return this.sampleBuff;
	}

	private short[] ByteArrayToSamplesArray() {
		for (int i = 0, j = 0; i < this.buff.length - 1; i += 2, j++) {
			this.sampleBuff[j] = (short) ((ByteBuffer.wrap(this.buff, i, 2).order(java.nio.ByteOrder.LITTLE_ENDIAN)
					.getShort()) * this.volume);
		}
		return this.sampleBuff;
	}

	private byte[] SampleArrayByteArray() {
		int flagOfPrevSignal1 = FilterInfo.COUNT_OF_COEFS - 1;
		boolean sumPrevSignalAndCurrent = true;
		for (int i = 0, j = 0, p = 0; i < this.sampleBuff.length
				&& j < (this.buff.length/*
										 * this.BUFF_SIZE +
										 * ru.bmstu.www.filter.coefs.FilterInfo.
										 * COUNT_OF_COEFS - 1
										 */); i++, j += 2) {
			if (sumPrevSignalAndCurrent && p < flagOfPrevSignal1) {
				this.sampleBuff[i] += this.prevSignal[p];
				p++;
			} else {
				sumPrevSignalAndCurrent = false;
			}

			// if(this.sampleBuff.length - i <= FilterInfo.COUNT_OF_COEFS - 1) {
			// this.prevSignal[p]
			// p++;
			// }

			this.buff[j] = (byte) (this.sampleBuff[i]);
			this.buff[j + 1] = (byte) (this.sampleBuff[i] >>> 8);
		}

		// for(int i = this.sampleBuff.length - FilterInfo.COUNT_OF_COEFS, p =
		// 0; i < this.sampleBuff.length - 1; i++) {
		// this.prevSignal[p] = this.sampleBuff[i];
		// p++;
		// }

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

	public boolean getFftReady() {
		return this.FFTready;
	}

	public void resetToDefault() {
		this.isDelay = false;
		this.isOverdrive = false;
		this.overdriveCoef = 1.0;
		this.volume = 0.3;
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

				FFTready = false;
				this.fastFourierInput.fft(this.sampleBuff);

				if (this.isDelay)
					this.delay(this.sampleBuff);

				if (this.isOverdrive) {
					this.overdrive(sampleBuff);
				}

				equalizer.setInputSignal(this.sampleBuff);
				this.equalizer.equalization();
				this.sampleBuff = equalizer.getOutputSignal();

				this.fastFourierOutput.fft(this.sampleBuff);

				FFTready = true;

				setChanged();
				notifyObservers();

				this.buff = this.SampleArrayByteArray();
				sourceDataLine.write(this.buff, 0, this.buff.length - 1);
			}
			this.FFTready = false;
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
