package ru.bmstu.www.effects;



public class Delay extends Effect{
	
	private int delay;
	private int maxD = 25000;
	private int minD = 13000;
	private double coef = 1.0;
	
	public Delay() {
		super(); 
		this.delay = 17000;
	}
	
	public void setInputSampleStream(short[] inputAudioStream) {
		this.inputAudioStream = inputAudioStream;		
	}

	@Override
	public synchronized short[] createEffect() {
		
		short amplitude 	  = 0;
		short delayAmplitude  = 0;	
		int checkFlag         = 0;
//		int delay 	 		  = 17000;
		int position 		  = 0;
		
		for(int i = delay ; i < this.inputAudioStream.length; i ++) {
			//System.out.println(this.delay);
			amplitude = this.inputAudioStream[i];
			delayAmplitude = this.inputAudioStream[position];
			checkFlag = ( (( delayAmplitude) + (int)(0.9 * amplitude)));
			if(checkFlag < Short.MAX_VALUE && checkFlag > Short.MIN_VALUE) { 
				delayAmplitude = (short)checkFlag;
				this.inputAudioStream[position] =  delayAmplitude; 
				position += 1;                    
			} 			

		}
		return this.inputAudioStream;
	}
	
	
	public void setDelayCoef(double coef) {
		this.coef = coef;
		this.delay *= this.coef;
		if(this.delay > this.maxD)
			this.delay = this.maxD;
		if(this.delay < this.minD)
			this.delay = this.minD;
	}

	@Override
	public synchronized short[] getOutputAudioStream() {
		return this.inputAudioStream;
	}

}
