package ru.bmstu.www.util;

import java.util.Scanner;

import ru.bmstu.www.exception.EqualizerExceptions;

public class EqualizerUtil {

	public static int getSerialNumber(String stringWithSerialNumber) throws EqualizerExceptions.IncorrectArgumet {

		if (stringWithSerialNumber == null || stringWithSerialNumber.equals(""))
			throw new EqualizerExceptions.IncorrectArgumet("String cannot be null or empty");

		Integer serialNumber = null;

		Scanner scanner = new Scanner(stringWithSerialNumber);

		scanner.useDelimiter("[^0-9]+");

		if (scanner.hasNext())
			serialNumber = scanner.nextInt();

		if (scanner.hasNext()) {
			scanner.close();
			throw new EqualizerExceptions.IncorrectArgumet("More than one number in Slider id");
		}

		if (serialNumber == null) {
			scanner.close();
			throw new EqualizerExceptions.IncorrectArgumet("Slider id does not contain number id");
		}

		scanner.close();

		return serialNumber;
	}
}
