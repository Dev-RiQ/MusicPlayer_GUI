package music;

import java.util.Collections;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import _main.Panel;

public class SoundController {

	private Clip clip;//오디오 파일
	private static int count;
	private boolean isPause,isPlay,isStop,isNew;
	private Sound sound;
	private static final int AUDIO_SPEED = 44100;
	
	public boolean isPlay() {
		return isPlay;
	}

	public boolean isPause() {
		return isPause;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public static int getCount() {
		return count;
	}

	public Clip getClip() {
		return clip;
	}
	
	public int getClipPlayTime() {
		return clip.getFrameLength()/AUDIO_SPEED;
	}

	private static SoundController instance;
	public static SoundController getInstance() {
		if(instance == null) instance = new SoundController();
		return instance;
	}
	private SoundController() {
		sound = Sound.getInstance();
		setFile();
		count = 0;
	}
	
	/** play next music */
	public void setFile() {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(sound.getSoundURL().get(count++));
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {

		}
	}
	
	/** play select music in list */
	public void setFile(int idx) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(sound.getSoundURL().get(idx));
			clip = AudioSystem.getClip();
			clip.open(ais);
			count = idx+1;
		} catch (Exception e) {
			
		}
	}
	
	/** play select music */
	public void play(int idx) {
		clip.stop();
		Panel.getInstance().resetCurTime();
		setFile(idx);
		VolumeHandler.getInstance().volumeSetting();
		isStop = false;
		if(!isPause)
			isNew = true;
		isPause = false;
		clip.start();
		isPlay = true;
		SoundInfo.getInstance().setMusicInfo(Panel.getInstance());
	}
	
	/** play music */
	public void play() {
		if(isPlay) return;
		clip.stop();
		if(!isPause) setFile();
		VolumeHandler.getInstance().volumeSetting();
		isStop = false;
		if(!isPause)
			isNew = true;
		isPause = false;
		clip.start();
		isPlay = true;
		SoundInfo.getInstance().setMusicInfo(Panel.getInstance());
	}
	
	/** stop music */
	public void stop() {
		clip.stop();
		if(isPlay) {
			count--;
			isPlay = false;
			isStop = true;
			Panel.getInstance().resetCurTime();
		}
		if(isPause) {
			isPause = false;
			isStop = true;
			Panel.getInstance().resetCurTime();
		}
		SoundInfo.getInstance().setMusicLength(Panel.getInstance(), 0);
	}
	
	/** pause music */
	public void pause() {
		clip.stop();
		if(isPlay) {
			isPause = true;
			isPlay = false;
		}
	}
	
	/** move to before music */
	public void before() {
		clip.stop();
		if(isStop) count--;
		else count-=2;
		if(isPause) isPause = false;
		if(count < 0) count = sound.getListSIZE() - 1;
		isPlay = false;
		Panel.getInstance().resetCurTime();
		play();
	}
	
	/** move to next music */
	public void next() {
		clip.stop();
		if(isStop || !isPlay) count++;
		if(isPause) isPause = false;
		if(count == sound.getListSIZE()) count = 0;
		isPlay = false;
		Panel.getInstance().resetCurTime();
		play();
	}
	
	/** suffle music list */
	public void suffle() {
		clip.stop();
		count = 0;
		Collections.shuffle(sound.getSoundURL());
		sound.setSoundList();
		isPlay = false;
		isPause = false;
		Panel.getInstance().resetCurTime();
		play();
	}
}
