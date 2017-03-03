package ru.bmstu.www.effect;

import java.util.Observable;

import ru.bmstu.www.util.EqualizerMessages;

public abstract class Effect extends Observable {

	protected boolean isActive;
	protected double coef;

	public void setCoef(double coef) {
		this.coef = coef;
	}

	public abstract void pushSample(double sample);

	public abstract double popSample();
	
	public abstract void reset();

	public boolean isActive() {
		return this.isActive;
	}

	public void setStatus(boolean status) {
		setChanged();
		notifyObservers(EqualizerMessages.UPD_GAIN);
		this.isActive = status;
	}
}