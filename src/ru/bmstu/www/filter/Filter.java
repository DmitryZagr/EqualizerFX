package ru.bmstu.www.filter;

import java.util.concurrent.Callable;

import ru.bmstu.www.filter.coefs.FilterInfo;

public class Filter implements Callable<short[]>  {
	
	protected int countOfCoefs;
	protected double[] coefsFilter;
	protected short[] inputSignal;
	protected short[] outputSignal;
	protected double gain;
	
	public Filter(final int lenghtOfInputSignal){
		gain = 1.0;
		this.outputSignal = new short[lenghtOfInputSignal /*+ FilterInfo.COUNT_OF_COEFS*/];
//		this.outputSignal = new short[inputSignal.length + countOfCoefs];
	}
	
//	public Filter(double[] coefsFilter, int countOfCoefs, short[] inputSignal) {
//	}
	
	public void settings(final double[] coefsFilter, final int countOfCoefs, final short[] inputSignal) {
		this.inputSignal = inputSignal;
		this.coefsFilter =  coefsFilter;
		this.countOfCoefs = countOfCoefs;
		this.outputSignal = new short[inputSignal.length /*+ countOfCoefs*/];
	}
	
	private short[] svertka() {
		int multiplication;
//		System.out.println(this.inputSignal.length);
		for(int i = 0; i <  inputSignal.length - FilterInfo.COUNT_OF_COEFS; i++) {
			for(int j = 0; j < this.countOfCoefs; j++) {
				
				multiplication =  (int) (this.inputSignal[i] * this.coefsFilter[j]);
				if(gain == 1.0) 
					this.outputSignal[i + j] += 0.145 * (short)(multiplication ); 
				else 
					this.outputSignal[i + j] += 0.13  * gain * (short)(multiplication );
			}	
		}
		return this.outputSignal;
	}

	public void setGain(float d) {
		this.gain = d;
	}
	
	public double getGain() {
		return this.gain;
	}
	
	public short[] getOutputSignal() {
		return this.outputSignal;
	}
	
	public long getCountOfSamples() {
		return this.inputSignal.length;
	}

	@Override
	public short[] call() throws Exception {
		this.svertka();
		return this.outputSignal;
	}

}

