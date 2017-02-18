package ru.bmstu.www.equalizer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import ru.bmstu.www.filter.Filter;
import ru.bmstu.www.filter.coefs.FilterInfo;

public class Equalizer {

	private short[] inputSignal;
	private short[] outputSignal;
	private Filter[] filters;
	private final static int COUNT_OF_BANDS = 7;
	private static int COUNT_OF_THREADS = 2;
	private final int lenghtOfInputSignal;
	private ExecutorService pool;
	private Future<double[]>[] futureTask;
	private static double NORMALIZE = 0.1;

	public Equalizer(final int lenghtOfInputSignal) {

		COUNT_OF_THREADS = Runtime.getRuntime().availableProcessors();

		if (Equalizer.COUNT_OF_THREADS == 1)
			Equalizer.COUNT_OF_THREADS = 2;

		if (Equalizer.COUNT_OF_THREADS > Equalizer.COUNT_OF_BANDS)
			Equalizer.COUNT_OF_THREADS = Equalizer.COUNT_OF_BANDS;

		this.futureTask = new Future[Equalizer.COUNT_OF_BANDS];

		this.pool = Executors.newFixedThreadPool(COUNT_OF_THREADS);
		this.lenghtOfInputSignal = lenghtOfInputSignal;
		this.createFilters();
	}

	public void setInputSignal(short[] inputSignal) {
		this.inputSignal = inputSignal;
		this.outputSignal = new short[this.lenghtOfInputSignal];
		for (Filter filter : this.filters)
			filter.setInputSignal(inputSignal);
	}

	private void createFilters() {

		this.filters = new Filter[Equalizer.COUNT_OF_BANDS];

		for (int i = 0; i < filters.length; i++) {
			this.filters[i] = Filter
					.settings(FilterInfo.CoefsOfBands[i], FilterInfo.COUNT_OF_COEFS, this.lenghtOfInputSignal).build();
		}
	}

	public void equalization() throws InterruptedException, ExecutionException {

		for (int i = 0; i < Equalizer.COUNT_OF_BANDS; i++) {
			futureTask[i] = pool.submit(this.filters[i]);
		}

		double sum = 0.0;

		for (int i = 0; i < this.outputSignal.length; i++) {
			for (Future<double[]> task : futureTask) {
				sum += task.get()[i];
				sum *= NORMALIZE;
				this.outputSignal[i] += sum;
			}

			sum = 0.0;
		}
	}

	public Filter getFilter(int nF) {
		return this.filters[nF];
	}
	
	public Filter[] getFilters() {
		return this.filters;
	}

	public short[] getOutputSignal() {
		return this.outputSignal;
	}

	public void close() {
		if (this.pool != null) {
			this.pool.shutdown();
		}
	}

}
