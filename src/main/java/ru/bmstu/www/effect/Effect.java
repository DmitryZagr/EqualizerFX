package ru.bmstu.www.effect;

public abstract class Effect {

	protected boolean isActive;
	protected short[] inputAudioStream;

	public abstract short[] createEffect();

	public short[] getOutputAudioStream() {
		return this.inputAudioStream;
	}

	public void setInputSampleStream(short[] inputAudioStream) {
		this.inputAudioStream = inputAudioStream;
	}

	public boolean isActive() {
		return this.isActive;
	}

	public void setStatus(boolean status) {
		this.isActive = status;
	}
}