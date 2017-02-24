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

	// @Override
	// public synchronized short[] createEffect() {
	// this.setMaxAndMinAmpl();
	// for (int i = 0; i < this.inputAudioStream.length; i++) {
	// if (this.inputAudioStream[i] > this.maxAmplitude)
	// this.inputAudioStream[i] = (this.maxAmplitude);
	// else if (this.inputAudioStream[i] < this.minAmplitude)
	// this.inputAudioStream[i] = (this.minAmplitude);
	// }
	// return this.inputAudioStream;
	// }

	private void createEffect() {
		this.setMaxAndMinAmpl();
		this.currentSample = this.currentSample > this.standartMax ? this.maxAmplitude
				: this.currentSample < this.minAmplitude ? this.minAmplitude : this.currentSample;
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