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
	private static int COUNT_OF_THREADS;
	private final int lenghtOfInputSignal;
	private ExecutorService pool;
	
	public Equalizer(final int lenghtOfInputSignal) {
		
		COUNT_OF_THREADS = Runtime.getRuntime().availableProcessors();
		if(Equalizer.COUNT_OF_THREADS == 1)
			Equalizer.COUNT_OF_THREADS = 2;
		
		pool = Executors.newFixedThreadPool(COUNT_OF_THREADS);
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
//		this.filters[7].settings(FilterInfo.CoefsOfBand_7, 
//				FilterInfo.COUNT_OF_COEFS, this.inputSignal);
//		this.filters[8].settings(FilterInfo.CoefsOfBand_8, 
//				FilterInfo.COUNT_OF_COEFS, this.inputSignal);
	}
	
	
	private void createFilters() {
		this.filters = new  Filter [Equalizer.COUNT_OF_BANDS] ;
		this.filters[0] = new Filter(this.lenghtOfInputSignal);
		this.filters[1] = new Filter(this.lenghtOfInputSignal);
		this.filters[2] = new Filter(this.lenghtOfInputSignal);
		this.filters[3] = new Filter(this.lenghtOfInputSignal);
		this.filters[4] = new Filter(this.lenghtOfInputSignal);
		this.filters[5] = new Filter(this.lenghtOfInputSignal);
		this.filters[6] = new Filter(this.lenghtOfInputSignal);
//		this.filters[7] = new Filter(this.lenghtOfInputSignal);
//		this.filters[8] = new Filter(this.lenghtOfInputSignal);
	}
	
	public void equalization( ) throws InterruptedException, ExecutionException {
//		pool = Executors.newFixedThreadPool(Equalizer.COUNT_OF_THREADS);
		Future<short[]>[] fs = new Future[Equalizer.COUNT_OF_BANDS]; 
		for(int i = 0; i < Equalizer.COUNT_OF_BANDS; i++) { 
			fs[i] = pool.submit(this.filters[i]);
		}
		
		for(int i = 0; i < this.outputSignal.length; i++) {
			this.outputSignal[i] += fs[0].get()[i] +
									fs[1].get()[i] +
									fs[2].get()[i] +
									fs[3].get()[i] +
									fs[4].get()[i] +
									fs[5].get()[i] +
									fs[6].get()[i] ;
//									fs[7].get()[i] +
//									fs[8].get()[i] ;
		}
	}
	
	public Filter getFilter(short nF) {
		return this.filters[nF];
	}
	
	public short[] getOutputSignal() {
		try {
			Thread.sleep(33);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.outputSignal;
	}
	
	public void close() {
		if(this.pool != null) {
			this.pool.shutdown();
		}
	}
	
}
