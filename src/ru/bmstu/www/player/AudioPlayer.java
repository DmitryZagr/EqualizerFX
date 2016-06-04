package ru.bmstu.www.player;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import ru.bmstu.www.effects.Delay;
import ru.bmstu.www.effects.Overdrive;
import ru.bmstu.www.equalizer.Equalizer;
import ru.bmstu.www.fft.FFT;
import ru.bmstu.www.input.AudioFileFormat;
import ru.bmstu.www.input.ReadMusicFile;

public class AudioPlayer {
	private double volume;
	private SourceDataLine sourceDataLine;
	private AudioInputStream ais;
	private byte[] buff;
//	private final int BUFF_SIZE = 8192 * 2/ 1 / 1 + ru.bmstu.www.filter.coefs.FilterInfo.COUNT_OF_COEFS;
	private final int BUFF_SIZE = 20480   + ru.bmstu.www.filter.coefs.FilterInfo.COUNT_OF_COEFS; 
//	20101

	private short[] sampleBuff;
	
	private Delay delay;
	private boolean isDelay;
	
	private Overdrive overdrive;
	private double overdriveCoef;
	private boolean isOverdrive;
	
	private Equalizer equalizer;
	private boolean pause;
	private AudioFormat format;
	private FFT fastFourierInput;
	private FFT fastFourierOutput;
	private boolean FFTready = false;
	private short[] prevSignal;
	
	public AudioPlayer(File musicFile) throws UnsupportedAudioFileException, 
						IOException, InterruptedException, LineUnavailableException {
		ReadMusicFile readFile = new ReadMusicFile(musicFile);
		
		this.sourceDataLine =  readFile.getSourceDataLine();
		this.ais = readFile.getAudioInputStream();
		this.buff = new byte[this.BUFF_SIZE];
		this.sampleBuff = new short[BUFF_SIZE >>> 1 ];
		this.delay = new Delay();
		this.overdrive = new Overdrive();
		this.isDelay = false;
		this.isOverdrive = false;
		this.overdriveCoef = 1.0;
		this.equalizer = new Equalizer(BUFF_SIZE >>> 1);
		AudioFileFormat aff = new AudioFileFormat();
		format = new AudioFormat((float)aff.getSampleRate(), 
				aff.getBits(), aff.getChannels(), 
				aff.isSigned(), aff.isBigEndian());
		this.volume = 1.0;
		this.fastFourierInput = new FFT();
		this.fastFourierOutput = new FFT();
		this.prevSignal = new short[ru.bmstu.www.filter.coefs.FilterInfo.COUNT_OF_COEFS - 1];
	}
	
	public void play() {
			try{
				this.sourceDataLine.open(this.format); 
				this.sourceDataLine.start();
				this.pause = false;
				while ((this.ais.read(this.buff, 0, this.BUFF_SIZE)) != -1) {
					
					this.ByteArrayToSamplesArray();
					
					FFTready = false;
					this.fastFourierInput.fft(this.sampleBuff);
					
					if(this.pause) {this.stop();}
					
					if(this.isDelay)
						this.delay(this.sampleBuff);
					
					if(this.isOverdrive) {
						this.overdrive(sampleBuff);
					}
					
					equalizer.setInputSignal(this.sampleBuff);
					this.equalizer.equalization();
					this.sampleBuff = equalizer.getOutputSignal();
					
					this.fastFourierOutput.fft(this.sampleBuff);
					
					FFTready = true;
					
					this.SampleArrayByteArray();
					sourceDataLine.write(this.buff, 0, (this.buff.length / 4 ) * 4);
				} System.out.println("END");
				this.FFTready = false;
				this.sourceDataLine.drain();
				this.sourceDataLine.close();
			}catch (Exception e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	
	private void stop() {
		if(pause) {
			for(;;) {
				try {
					if(!pause) break;
					this.FFTready = false;
					Thread.sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
		}
	}
	
	public void setPause(boolean pause) {
		this.pause = pause;
	}
	
	public boolean getPause() {
		return this.pause;
	}
	
	public void setVolume(double volume) {
		this.volume = volume;
	}
	
	public double getVolume() {
		return this.volume;
	}

//	@Override
//	public void update(LineEvent event) {
//		LineEvent.Type type = event.getType();
//	}
	
	public short[] getSamples() {
		return this.sampleBuff;
	}
	
	private void ByteArrayToSamplesArray() {
		for(int i = 0, j = 0; i < this.buff.length - 1; i += 2 , j++) {
			this.sampleBuff[j] = (short) (0.5 *  (ByteBuffer.wrap(this.buff, i, 2).order(
					java.nio.ByteOrder.LITTLE_ENDIAN).getShort()) * this.getVolume());
//			System.out.println(this.sampleBuff[j]);
//			System.out.println(this.sampleBuff[j]);
		}
	}
	
	private void SampleArrayByteArray() {
		int flagOfPrevSignal1 = ru.bmstu.www.filter.coefs.FilterInfo.COUNT_OF_COEFS - 1;
		for(int i = 0, j = 0, p = 0; i < this.sampleBuff.length && j < (this.buff.length/*this.BUFF_SIZE  + 
				ru.bmstu.www.filter.coefs.FilterInfo.COUNT_OF_COEFS - 1 */); i++, j += 2, p++ ) {
			if( p < flagOfPrevSignal1) {
				this.sampleBuff[i] += this.prevSignal[p];
			}
				
			this.buff[j] = (byte)(this.sampleBuff[i]);
			this.buff[j + 1] = (byte)(this.sampleBuff[i] >>> 8);
//			System
			
		}
//		int flagOfPrevSignal2 = this.sampleBuff.length - 
//										ru.bmstu.www.filter.coefs.FilterInfo.COUNT_OF_COEFS - 2;
//		for(int i = flagOfPrevSignal2, j = 0; j < 
//							ru.bmstu.www.filter.coefs.FilterInfo.COUNT_OF_COEFS - 1; 
//																			i++ , j++) {
//			this.prevSignal[j] =
//					this.sampleBuff[i];
//		}
		
	}
	
	public Equalizer getEqualizer() {
		return this.equalizer;
	}
	
	public void close() {
		if(this.ais != null)
			try {
				this.ais.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(this.sourceDataLine != null)
			this.sourceDataLine.close();
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
	
}






