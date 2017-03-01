package ru.bmstu.www.player;

import java.io.File;

public interface IAudioPlayer {

	public void play();

	public void pause();

	public void resume();

	public void stop();

	public void setMusicFile(File file);
}
