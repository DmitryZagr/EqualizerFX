package ru.bmstu.www.filter;

import java.util.concurrent.Callable;

public class Filter implements Callable<double[]> {

	protected double[] coefsFilter;
	protected short[] inputSignal;
	protected double[] outputSignal;
	protected double gain;
	protected short[] buff;
	protected final static double dB = 1.259;

	public static class FilterBuilder {
		private double[] coefsFilter;
		private double[] outputSignal;
		private double gain;
		private short[] buff;
		
		public FilterBuilder coefsFilter(double[] coefsFilter) {
			this.coefsFilter = coefsFilter;
			this.buff = new short[(this.coefsFilter.length)];
			return this;
		}
		
		public FilterBuilder lenghtOfInputSignal(int lenghtOfInputSignal) {
			this.outputSignal = new double[lenghtOfInputSignal];
			return this;
		}
		
		public FilterBuilder gain(double gain) {
			this.gain = gain;
			return this;
		}
		
		
		public  Filter build() {
			return new Filter(this);
		}
		
	}
	
	private  Filter(FilterBuilder filterBuilder) {
		this.gain = filterBuilder.gain;
		this.coefsFilter = filterBuilder.coefsFilter;
		this.buff = filterBuilder.buff;
		this.outputSignal = filterBuilder.outputSignal;
	}
	

	private double[] svertka() {

		for (int i = 0; i < inputSignal.length; i++) {
			this.outputSignal[i] = 0.0;

			System.arraycopy(this.buff, 1, this.buff, 0, buff.length - 1);
			this.buff[buff.length - 1] = this.inputSignal[i];

			for (int j = 0; j < this.coefsFilter.length - 1; j++) {
				this.outputSignal[i] += this.coefsFilter[j] * buff[buff.length - 1 - j];
			}
			this.outputSignal[i] *= Math.pow(10, gain / 20);
		}

		return this.outputSignal;
	}

	public void setInputSignal(short[] inputSignal) {
		this.inputSignal = inputSignal;
	}

	public void setGain(float d) {
		this.gain = d;
	}

	public double getGain() {
		return this.gain;
	}

	public double[] getOutputSignal() {
		return this.outputSignal;
	}

	public long getCountOfSamples() {
		return this.inputSignal.length;
	}

	@Override
	public double[] call() throws Exception {
		this.svertka();
		return this.outputSignal;
	}

}
