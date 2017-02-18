package ru.bmstu.www.util;

import java.util.Scanner;

import ru.bmstu.www.exception.EqualizerExceptions;

public class EqualizerUtil {

	public static int getSerialNumber(String stringWithSerialNumber) throws EqualizerExceptions.IncorrectArgumet {

		if (stringWithSerialNumber == null || stringWithSerialNumber.equals(""))
			throw new EqualizerExceptions.IncorrectArgumet("String cannot be null or empty");

		Integer serialNumber = null;

		Scanner s = new Scanner(stringWithSerialNumber).useDelimiter("[^0-9]+");

		if (s.hasNext())
			serialNumber = s.nextInt();

		if (s.hasNext())
			throw new EqualizerExceptions.IncorrectArgumet("More than one number in Slider id");

		if (serialNumber == null)
			throw new EqualizerExceptions.IncorrectArgumet("Slider id does not contain number id");

		return serialNumber;
	}
}
