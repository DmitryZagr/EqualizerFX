package ru.bmstu.www.exception;

public class EqualizerExceptions {
	
	public static class IncorrectInit extends RuntimeException {
		
		public IncorrectInit(String message){
			super(message);
		}
	}
	
	public static class IncorrectArgumet extends RuntimeException {
		
		public IncorrectArgumet(String message){
			super(message);
		}
	}
	
}
