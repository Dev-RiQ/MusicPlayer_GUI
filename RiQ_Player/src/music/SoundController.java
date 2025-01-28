package music;

import java.util.Collections;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import _main.Panel;
import button.Button;
import list.MusicList;

public class SoundController {

	private Clip clip;//오디오 파일
	private static int count;
	private boolean isPause,isPlay,isStop,isNew;
	private Sound sound;
	private Panel panel;
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
		panel = Panel.getInstance();
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
		panel.resetCurTime();
		setFile(idx);
		defaultPlaySet();
	}
	
	/** play music */
	public void play() {
		if(isPlay) return;
		clip.stop();
		if(!isPause) setFile();
		defaultPlaySet();
	}

	/** play music default setting */
	private void defaultPlaySet() {
		Image.getInstance().setImage(panel);
		MusicList.getInstance().setPosition(count - 1);
		VolumeHandler.getInstance().volumeSetting();
		if(!isPause)
			isNew = true;
		SoundInfo.getInstance().setMusicInfo(panel);
		isStop = false;
		isPause = false;
		isPlay = true;
		Button.getInstance().setPlayButton(isPlay);
		clip.start();
	}
	
	/** stop music */
	public void stop() {
		if(isStop) return;
		clip.stop();
		count--;
		isPlay = false;
		isPause = false;
		isStop = true;
		panel.resetCurTime();
		SoundInfo.getInstance().setMusicLength(panel, 0);
	}
	
	/** pause music */
	public void pause() {
		if(!isPlay) return;
		clip.stop();
		isPause = true;
		isPlay = false;
		Button.getInstance().setPlayButton(isPlay);
	}
	
	/** move to before music */
	public void before() {
		stop();
		if(isStop) count--;
		else count-=2;
		if(count < 0) count = sound.getListSIZE() - 1;
		play();
	}
	
	/** move to next music */
	public void next() {
		stop();
		if(isStop || !isPlay) count++;
		if(count >= sound.getListSIZE()) count = 0;
		play();
	}
	
	/** suffle music list */
	public void suffle() {
		stop();
		count = 0;
		Collections.shuffle(sound.getSoundURL());
		sound.setSoundList();
		MusicList.getInstance().positionReset();
		play();
	}
	
	/** time position adjust in parameter time */
	public void timeSet(int time) {
		if(!isPlay) return;
		panel.setCurTime(time);
		clip.setMicrosecondPosition((int) panel.getCurTime() * 1000000);
		clip.start();
	}
	
}
