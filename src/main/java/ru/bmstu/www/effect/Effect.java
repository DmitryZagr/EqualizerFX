package ru.bmstu.www.effect;

public abstract class Effect {

	protected boolean isActive;
	protected double coef;

	public void setCoef(double coef) {
		this.coef = coef;
	}

	public abstract void pushSample(double sample);

	public abstract double popSample();

	public boolean isActive() {
		return this.isActive;
	}

	public void setStatus(boolean status) {
		this.isActive = status;
	}
}