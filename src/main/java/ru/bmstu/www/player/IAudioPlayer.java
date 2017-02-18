package ru.bmstu.www.player;

public interface IAudioPlayer extends Runnable {

	public void run();

	public void pause();

	public void resume();

	public void stop();
}
