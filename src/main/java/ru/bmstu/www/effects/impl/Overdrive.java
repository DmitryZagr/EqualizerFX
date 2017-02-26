package ru.bmstu.www.effects.impl;

import ru.bmstu.www.effect.Effect;

public final class Overdrive extends Effect {

	private short maxAmplitude;
	private short minAmplitude;
	private static final short standartMax = 2000;
	private static final short standartMin = -2000;
	private double currentSample;

	public Overdrive() {
		super();
		this.coef = 0.5;
	}

	private void createEffect() {
		this.setMaxAndMinAmpl();
		if (this.currentSample > this.maxAmplitude)
			this.currentSample = this.maxAmplitude;
		else if (this.currentSample < this.minAmplitude)
			this.currentSample = this.minAmplitude;
	}

	private void setMaxAndMinAmpl() {
		this.maxAmplitude = (short) (Overdrive.standartMax * this.coef);
		this.minAmplitude = (short) (Overdrive.standartMin * this.coef);
	}

	public void setOverdriveCoef(double coef) {
		this.coef = coef;
	}

	@Override
	public void pushSample(double sample) {
		this.currentSample = sample;
		if (this.isActive)
			this.createEffect();
	}

	@Override
	public double popSample() {
		return this.currentSample;
	}
}