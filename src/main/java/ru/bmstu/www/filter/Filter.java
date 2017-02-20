package ru.bmstu.www.filter;

import java.util.Arrays;
import java.util.concurrent.Callable;

import ru.bmstu.www.filter.coefs.FilterInfo;

public class Filter implements Callable<double[]> {

	protected int countOfCoefs;
	protected double[] coefsFilter;
	protected short[] inputSignal;
	protected double[] outputSignal;
	protected double gain;

	private Filter(final double[] coefsFilter, final int countOfCoefs, int lenghtOfInputSignal) {
		gain = 1.0;
		this.coefsFilter = coefsFilter;
		this.countOfCoefs = countOfCoefs;
		this.outputSignal = new double[lenghtOfInputSignal /* + countOfCoefs */];
	}

	public static Filter settings(final double[] coefsFilter, final int countOfCoefs, int lenghtOfInputSignal) {
		return new Filter(coefsFilter, countOfCoefs, lenghtOfInputSignal);
	}

	public Filter build() {
		return this;
	}

	private double[] svertka() {
		double multiplication;

		Arrays.fill(this.outputSignal, 0);

		// for (int i = 0; i < this.outputSignal.length; i++) {
		// int count = this.outputSignal.length - i;
		// if (i < FilterInfo.COUNT_OF_COEFS)
		// count = i + 1;
		// else if (count < 0)
		// count += FilterInfo.COUNT_OF_COEFS;
		// else
		// count = FilterInfo.COUNT_OF_COEFS;
		// int j = 0;
		// int temp = 0;
		// while (j < count) {
		// temp += this.inputSignal[i - j] * this.coefsFilter[j];
		// j++;
		// }
		// if (temp > Short.MAX_VALUE || temp < Short.MIN_VALUE) {
		// temp = temp >>> 15;
		// System.out.println(temp);
		// }
		//
		// this.outputSignal[i] = (double) temp;
		// }

		for (int i = 0; i < inputSignal.length - FilterInfo.COUNT_OF_COEFS; i++) {
			for (int j = 0; j < this.countOfCoefs; j++) {
				multiplication = (double) this.inputSignal[i] * this.coefsFilter[j];
				this.outputSignal[i + j] += multiplication * gain;
			}
		}

		// for(int i = 0; i < this.outputSignal.length; i++ ) {
		// if(this.outputSignal[i] > 0)
		// System.out.println(this.outputSignal[i]);
		// }
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
