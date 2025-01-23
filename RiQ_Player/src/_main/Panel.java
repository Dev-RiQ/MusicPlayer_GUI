package _main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import music.SoundController;
import music.SoundInfo;

@SuppressWarnings("serial")
public class Panel extends JPanel implements Runnable {

	private final int screenWidth = 520;
	private final int screenHeight = 200;
	private Thread thread;
	private int curTime;
	
	public void resetCurTime() {
		curTime = 0;
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
		long currentTime = 0; // 2번 시간
		// 스레드 동작하는 동안 무한 반복
		while(thread != null) {
			currentTime = System.nanoTime();
			sec += (currentTime - lastTime) / timeInteval;
			lastTime = currentTime;
			if(sec >= 1) {
				sec = 0;
				if(SoundController.getInstance().isPlay())
					SoundInfo.getInstance().setMusicLength(this,++curTime);
				if(curTime >=SoundInfo.getInstance().getPlayTime()) { 
					SoundController.getInstance().next();
					curTime = 0;
				}
			}
		}
	}
	
}
