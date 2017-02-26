package ru.bmstu.www.filter;

import java.util.concurrent.Callable;

public class Filter implements Callable<double[]> {

	protected double[] coefsFilter;
	protected short[] inputSignal;
	protected double[] outputSignal;
	protected double gain;
	protected short[] buff;
	protected final static double dB = 1.259;

	private Filter(final double[] coefsFilter, int lenghtOfInputSignal) {
		gain = 1.0;
		this.coefsFilter = coefsFilter;
		this.outputSignal = new double[lenghtOfInputSignal];
		this.buff = new short[(this.coefsFilter.length)];
	}

	public static Filter settings(final double[] coefsFilter, int lenghtOfInputSignal) {
		return new Filter(coefsFilter, lenghtOfInputSignal);
	}

	public Filter build() {
		return this;
	}

	private double[] svertka() {

		for (int i = 0; i < inputSignal.length; i++) {
			this.outputSignal[i] = 0.0;

			System.arraycopy(this.buff, 1, this.buff, 0, buff.length - 1);
			this.buff[buff.length - 1] = this.inputSignal[i];

			for (int j = 0; j < this.coefsFilter.length - 1; j++) {
				this.outputSignal[i] += this.coefsFilter[j] * buff[buff.length - 1 - j] * Math.pow(10, gain / 20);
			}
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
