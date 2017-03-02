package ru.bmstu.www.filter;

import java.util.concurrent.Callable;

public class Filter implements Callable<double[]> {

	protected double[] coefsFilter;
	protected short[] inputSignal;
	protected double[] outputSignal;
	protected double gain;
	protected short[] buff;
	protected final static double dB = 1.259;
	protected final int FILTER_ID;

	public static class FilterBuilder {
		private double[] coefsFilter;
		private double[] outputSignal;
		private double gain;
		private short[] buff;
		private int id = this.hashCode();

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
		
		public FilterBuilder id(int id) {
			this.id = id;
			return this;
		}

		public Filter build() {
			return new Filter(this);
		}

	}

	private Filter(FilterBuilder filterBuilder) {
		this.FILTER_ID = filterBuilder.id;
		this.gain = filterBuilder.gain;
		this.coefsFilter = filterBuilder.coefsFilter;
		this.buff = filterBuilder.buff;
		this.outputSignal = filterBuilder.outputSignal;
	}

	private double[] convolution() {

		double gain = Math.pow(10, this.gain / 20);

		for (int i = 0; i < inputSignal.length; i++) {
			this.outputSignal[i] = 0.0;

			this.manageFilterMemory(this.inputSignal[i]);

			for (int j = 0; j < this.coefsFilter.length - 1; j++) {
				this.outputSignal[i] += this.coefsFilter[j] * buff[buff.length - 1 - j];
			}
			this.outputSignal[i] *= gain;
		}

		return this.outputSignal;
	}

	private short[] manageFilterMemory(short newSample) {
		System.arraycopy(this.buff, 1, this.buff, 0, buff.length - 1);
		this.buff[buff.length - 1] = newSample;
		return this.buff;
	}

	public void setInputSignal(short[] inputSignal) {
		this.inputSignal = inputSignal;
	}

	public void setGain(float d) {
		this.gain = d;
	}

	public double[] getOutputSignal() {
		return this.outputSignal;
	}

	@Override
	public double[] call() throws Exception {
		this.convolution();
		return this.outputSignal;
	}

}
