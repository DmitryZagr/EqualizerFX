package ru.bmstu.www.effects.impl;

import java.util.ArrayDeque;
import java.util.Deque;

import ru.bmstu.www.effect.Effect;

public final class Delay extends Effect {

	private final int DEFAULT = 25000;
	private int delay = DEFAULT;
	private Deque<Double> dequeSamples = new ArrayDeque<>(delay);
	private double currentSample;

	@Override
	public void pushSample(double sample) {
		currentSample = sample;
		if(!isActive) {
			dequeSamples.clear();
			return;
		}
		if (delay >= this.dequeSamples.size())
			this.dequeSamples.addLast(sample);
		else {
			while (this.delay < this.dequeSamples.size()) {
				this.dequeSamples.pop();
			}
		}
	}

	@Override
	public double popSample() {
		if (this.isActive) {
			return this.createEffect();
		}
		return 0.0;
	}

	private double createEffect() {
		if (this.delay == this.dequeSamples.size() && this.dequeSamples.size() != 0) {
			return (this.dequeSamples.getLast() + (this.dequeSamples.pop() * 0.8));
		}
		return currentSample;
	}

	@Override
	public void setCoef(double coef) {
		this.delay = DEFAULT;
		this.delay *= coef;
	}

	@Override
	public void reset() {
		this.dequeSamples.clear();
	}

}
