package _main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import lengthLine.MusicLengthLine;
import music.SoundController;
import music.SoundInfo;

@SuppressWarnings("serial")
public class Panel extends JPanel implements Runnable {

	private final int screenWidth = 460;
	private final int screenHeight = 275;
	private Thread thread;
	private double curTime;
	
	public void resetCurTime() {
		curTime = 0;
	}

	public void setCurTime(double curTime) {
		this.curTime = curTime;
	}

	public double getCurTime() {
		return curTime;
	}

	private Panel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
	}

	private static Panel instance;
	public static Panel getInstance() {
		if(instance == null) instance = new Panel();
		return instance;
	}

	/** Thread On */
	public void startThread() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		double timeInteval = 1000000000;
		double sec = 0;
		long lastTime = System.nanoTime();
		long currentTime = 0;
		while(thread != null) {
			// refresh time in music play
			if(SoundController.getInstance().isNew()) {
				SoundInfo.getInstance().setMusicInfo(this);
				SoundInfo.getInstance().setMusicLength(this,0);
				sec = 0;
				lastTime = System.nanoTime();
				currentTime = 0;
				SoundController.getInstance().setNew(false);
			}
			currentTime = System.nanoTime();
			// don't calculate time when it pause
			if(!SoundController.getInstance().isPause()) {
				sec += (currentTime - lastTime) / timeInteval;
			}
			lastTime = currentTime;
			if(((int)(curTime % (SoundInfo.getInstance().getPlayTime() *1.0 / 60))) == 0) {
				MusicLengthLine.getInstance().playTimeLine((int)(curTime / (SoundInfo.getInstance().getPlayTime() * 1.0 / 60)));
			}
			if(sec >= 1) {
				sec = 0;
				if(SoundController.getInstance().isPlay())
					SoundInfo.getInstance().setMusicLength(this,(int)++curTime);
				if(curTime >=SoundInfo.getInstance().getPlayTime()) { 
					SoundController.getInstance().next();
					curTime = 0;
				}
			}
		}
	}
	
	
}
