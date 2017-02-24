package ru.bmstu.www.equalizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ru.bmstu.www.effect.Effect;
import ru.bmstu.www.filter.Filter;
import ru.bmstu.www.filter.coefs.FilterInfo;

public class Equalizer {

	private short[] inputSignal;
	private short[] outputSignal;
	private Filter[] filters;
	private final int COUNT_OF_BANDS = 7;
	private int COUNT_OF_THREADS = 2;
	private final int lenghtOfInputSignal;
	private ExecutorService pool;
	private Future<double[]>[] futureTasks;
	private static final double NORMALIZE = 0.1;
	private double volume = 1.0;
	private Map<String, Effect> effects = new HashMap<>();

	@SuppressWarnings("unchecked")
	public Equalizer(final int lenghtOfInputSignal) {

		COUNT_OF_THREADS = Runtime.getRuntime().availableProcessors();

		if (COUNT_OF_THREADS == 1)
			COUNT_OF_THREADS = 2;
		else if (COUNT_OF_THREADS > COUNT_OF_BANDS)
			COUNT_OF_THREADS = COUNT_OF_BANDS;
		else
			COUNT_OF_THREADS = 3;

		this.futureTasks = new Future[COUNT_OF_BANDS];

		this.pool = Executors.newFixedThreadPool(COUNT_OF_THREADS);
		this.lenghtOfInputSignal = lenghtOfInputSignal;
		this.outputSignal = new short[this.lenghtOfInputSignal];
		this.createFilters();
	}

	public Equalizer bindEffect(String effectName, Effect effect) {
		this.effects.put(effectName, effect);
		return this;
	}

	public Effect getEffect(String effectName) {
		return this.effects.get(effectName);
	}

	public Equalizer unbindEffect(String effectName) {
		this.effects.remove(effectName);
		return this;
	}

	public Equalizer setInputSignal(short[] inputSignal) {
		this.inputSignal = inputSignal;
		this.outputSignal = new short[this.lenghtOfInputSignal];
		for (Filter filter : this.filters)
			filter.setInputSignal(this.inputSignal);
		return this;
	}

	private Equalizer createFilters() {

		this.filters = new Filter[COUNT_OF_BANDS];

		for (int i = 0; i < filters.length; i++) {
			this.filters[i] = Filter.settings(FilterInfo.CoefsOfBands[i], this.lenghtOfInputSignal).build();
		}

		return this;
	}

	public void equalization() throws InterruptedException, ExecutionException {

		for (int i = 0; i < COUNT_OF_BANDS; i++) {
			futureTasks[i] = pool.submit(this.filters[i]);
		}

		double sum = 0.0;

		for (int i = 0; i < this.outputSignal.length; i++) {
			for (Future<double[]> task : futureTasks) {
				sum += task.get()[i];
				sum *= NORMALIZE * this.getVolume();
				this.outputSignal[i] += sum;
			}
			final int index = i;

			effects.forEach((k, e) -> {
				if (e.isActive()) {
					e.pushSample(this.outputSignal[index]);
					this.outputSignal[index] = (short) e.popSample();
				}
			});

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

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getVolume() {
		return this.volume;
	}

}
