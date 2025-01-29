package _main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import lengthLine.MusicLengthLine;
import music.Sound;
import music.SoundController;
import music.SoundInfo;

@SuppressWarnings("serial")
public class Panel extends JPanel implements Runnable {

	private final int screenWidth = 360;
	private final int screenHeight = 580;
	private Thread thread;
	private double curTime;
	
	private SoundInfo soundInfo;
	private SoundController soundController;
	
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
		this.setBackground(Color.DARK_GRAY);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.setBorder(new LineBorder(Color.GRAY,2,true));
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
		soundInfo = SoundInfo.getInstance();
		soundController = SoundController.getInstance();
	}
	
	/** Thread Off */
	public void endThread() {
		thread = null;
		soundController.stop();
		Frame.getInstance().dispose();
	}

	@Override
	public void run() {
		double timeInteval = 1000000000;
		double sec = 0;
		long lastTime = System.nanoTime();
		long currentTime = 0;
		while(thread != null) {
			// refresh time in music play
			if(soundController.isNew()) {
				soundInfo.setMusicInfo(this);
				soundInfo.setMusicLength(this,0);
				sec = 0;
				lastTime = System.nanoTime();
				currentTime = 0;
				soundController.setNew(false);
			}
			currentTime = System.nanoTime();
			// don't calculate time when it pause
			if(!soundController.isPause()) {
				sec += (currentTime - lastTime) / timeInteval;
			}
			lastTime = currentTime;
			MusicLengthLine.getInstance().playTimeLine((int)(curTime / (SoundInfo.getInstance().getPlayTime() * 1.0 / 47)));
			if(sec >= 1) {
				sec = 0;
				if(soundController.isPlay())
					soundInfo.setMusicLength(this,(int)++curTime);
				if(curTime >=SoundInfo.getInstance().getPlayTime()) { 
					soundController.next();
					curTime = 0;
				}
			}
		}
		Sound.getInstance().savePlayList("playList.txt",Sound.getInstance().getPlayList());
	}
	
	
}
