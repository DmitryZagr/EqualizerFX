package ru.bmstu.www.equalizer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import ru.bmstu.www.filter.Filter;
import ru.bmstu.www.filter.coefs.FilterInfo;

public class Equalizer {
	
	private short[] inputSignal;
	private short[]outputSignal;
	private Filter[] filters;
	private final static int COUNT_OF_BANDS = 7;
	private static int COUNT_OF_THREADS = 2;
	private final int lenghtOfInputSignal;
	private ExecutorService pool;
	private Future<short[]>[] futureTask; 
	
	public Equalizer(final int lenghtOfInputSignal) {
		
		COUNT_OF_THREADS = Runtime.getRuntime().availableProcessors();
		
		if(Equalizer.COUNT_OF_THREADS == 1)
			Equalizer.COUNT_OF_THREADS = 2;
		
		if(Equalizer.COUNT_OF_THREADS > Equalizer.COUNT_OF_BANDS)
			Equalizer.COUNT_OF_THREADS = Equalizer.COUNT_OF_BANDS;
		
		this.futureTask = new Future[Equalizer.COUNT_OF_BANDS]; 
		
		this.pool = Executors.newFixedThreadPool(COUNT_OF_THREADS);
		this.lenghtOfInputSignal = lenghtOfInputSignal;
		this.createFilters();
	}
	
	public void setInputSignal(short[] inputSignal) {
		this.inputSignal = inputSignal;
		this.outputSignal = new short[this.lenghtOfInputSignal];
		this.filters[0].settings(FilterInfo.CoefsOfBand_0, 
				FilterInfo.COUNT_OF_COEFS, this.inputSignal);
		this.filters[1].settings(FilterInfo.CoefsOfBand_1, 
				FilterInfo.COUNT_OF_COEFS, this.inputSignal);
		this.filters[2].settings(FilterInfo.CoefsOfBand_2, 
				FilterInfo.COUNT_OF_COEFS, this.inputSignal);
		this.filters[3].settings(FilterInfo.CoefsOfBand_3, 
				FilterInfo.COUNT_OF_COEFS, this.inputSignal);
		this.filters[4].settings(FilterInfo.CoefsOfBand_4, 
				FilterInfo.COUNT_OF_COEFS, this.inputSignal);
		this.filters[5].settings(FilterInfo.CoefsOfBand_5, 
				FilterInfo.COUNT_OF_COEFS, this.inputSignal);
		this.filters[6].settings(FilterInfo.CoefsOfBand_6, 
				FilterInfo.COUNT_OF_COEFS, this.inputSignal);
	}
	
	
	private void createFilters() {
		
		this.filters = new  Filter [Equalizer.COUNT_OF_BANDS];
		
		this.filters[0] = new Filter(this.lenghtOfInputSignal);
		this.filters[1] = new Filter(this.lenghtOfInputSignal);
		this.filters[2] = new Filter(this.lenghtOfInputSignal);
		this.filters[3] = new Filter(this.lenghtOfInputSignal);
		this.filters[4] = new Filter(this.lenghtOfInputSignal);
		this.filters[5] = new Filter(this.lenghtOfInputSignal);
		this.filters[6] = new Filter(this.lenghtOfInputSignal);
	}
	
	public void equalization() throws InterruptedException, ExecutionException {
		
		for(int i = 0; i < Equalizer.COUNT_OF_BANDS; i++) { 
			futureTask[i] = pool.submit(this.filters[i]);
		}
		
		for(int i = 0; i < this.outputSignal.length; i++) {
			this.outputSignal[i] += futureTask[0].get()[i] +
									futureTask[1].get()[i] +
									futureTask[2].get()[i] +
									futureTask[3].get()[i] +
									futureTask[4].get()[i] +
									futureTask[5].get()[i] +
									futureTask[6].get()[i] ;
		}
	}
	
	public Filter getFilter(short nF) {
		return this.filters[nF];
	}
	
	public short[] getOutputSignal() {
		return this.outputSignal;
	}
	
	public void close() {
		if(this.pool != null) {
			this.pool.shutdown();
		}
	}
	
}
