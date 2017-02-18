package ru.bmstu.www.exception;

public class EqualizerExceptions {
	
	public static class IncorrectInit extends RuntimeException {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public IncorrectInit(String message){
			super(message);
		}
	}
	
	public static class IncorrectArgumet extends RuntimeException {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public IncorrectArgumet(String message){
			super(message);
		}
	}
	
}
