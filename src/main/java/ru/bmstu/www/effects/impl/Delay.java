package ru.bmstu.www.effects.impl;

import java.util.ArrayDeque;
import java.util.Deque;

import ru.bmstu.www.effect.Effect;

//NOT WORK
public final class Delay extends Effect {

	private int delay = 10000;
	private Deque<Double> dequeSamples = new ArrayDeque<>(delay);

	@Override
	public void pushSample(double sample) {
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
		if (this.delay <= this.dequeSamples.size())
			return this.dequeSamples.getFirst() + (this.dequeSamples.pop() * this.coef);
		return 0.0;
	}

	@Override
	public void setCoef(double coef) {
		this.coef = coef;
		this.delay *= this.coef;
	}

}
