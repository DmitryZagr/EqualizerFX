package ru.bmstu.www.equalizer;

import java.util.HashMap;
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
	private final int countOfBands;
	private int countOfThreads;
	private final int lenghtOfInputSignal;
	private ExecutorService pool;
	private Future<double[]>[] futureTasks;
	private static final double NORMALIZE = 0.05;
	private double volume = 0.5;
	private Map<String, Effect> effects = new HashMap<>();

	
	@SuppressWarnings("unchecked")
	private Equalizer(EqualizerBuilder equalizerBuilder) {

		this.countOfBands = equalizerBuilder.countOfBands;
		this.countOfThreads = equalizerBuilder.countOfThreads;
		this.lenghtOfInputSignal = equalizerBuilder.lenghtOfInputSignal;
		
		this.futureTasks = new Future[countOfBands];

		this.pool = Executors.newFixedThreadPool(countOfThreads);
		this.outputSignal = new short[this.lenghtOfInputSignal];
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
		for (Filter filter : this.filters)
			filter.setInputSignal(this.inputSignal);
		return this;
	}

	private Equalizer createFilters() {

		this.filters = new Filter[countOfBands];

		Filter.FilterBuilder filterBuilder = new Filter.FilterBuilder();

		for (int i = 0; i < filters.length; i++) {
			this.filters[i] = filterBuilder.gain(1.0).coefsFilter(FilterInfo.CoefsOfBands[i])
					.lenghtOfInputSignal(this.lenghtOfInputSignal).build();
		}

		return this;
	}

	public void equalization() throws InterruptedException, ExecutionException {

		for (int i = 0; i < countOfBands; i++) {
			futureTasks[i] = pool.submit(this.filters[i]);
		}

		double sum = 0.0;

		for (int i = 0; i < this.outputSignal.length; i++) {
			this.outputSignal[i] = 0;
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

	public void setDefaults() {
		effects.forEach((k, e) -> {
			e.setStatus(false);
		});
		this.volume = 0.5;
	}

	public static class EqualizerBuilder {
		private int countOfBands = 7;
		private int countOfThreads;
		private int lenghtOfInputSignal = 512;

		public EqualizerBuilder countOfBands(int count) {
			this.countOfBands = count;
			return this;
		}

		public EqualizerBuilder countOfThreads(int count) {
			this.countOfThreads = count;
			return this;
		}

		public EqualizerBuilder lenghtOfInputSignal(int lenght) {
			this.lenghtOfInputSignal = lenght;
			return this;
		}

		public Equalizer build() {
			if (this.countOfThreads == 0)
				defaultCountOfThreads();
			return new Equalizer(this);
		}

		private void defaultCountOfThreads() {
			countOfThreads = Runtime.getRuntime().availableProcessors();

			if (countOfThreads == 1)
				countOfThreads = 2;
			else if (countOfThreads > countOfBands)
				countOfThreads = countOfBands;
			else
				countOfThreads = 3;
		}
	}

}
