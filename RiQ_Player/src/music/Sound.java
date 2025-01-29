package music;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import list.MusicList;

public class Sound{

	private List<URL> soundURL;
	private List<String> soundList;
	private final String filePath = System.getProperty("user.dir") + "\\res\\";
	private MusicList musicList;

	public List<URL> getSoundURL() {
		return soundURL;
	}
	
	public int getListSIZE() {
		return soundURL.size();
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public String getFileName(int idx) {
		return soundList.get(idx);
	}

	@SuppressWarnings("static-access")
	public String getURL_Path() {
		int count = SoundController.getInstance().getCount();
		count = count <= 0? 0 : count -1;
		return soundURL.get(count).getPath();
	}

	private Sound() {
		setSoundURL();
	}
	private static Sound instance;
	public static Sound getInstance() {
		if(instance == null) instance = new Sound();
		return instance;
	}
	
	/** set music file URL list */
	@SuppressWarnings("deprecation")
	private void setSoundURL() {
		String[] list = getMusicList("music");
		soundURL = new ArrayList<>();
		for (int i = 0; i < list.length; i++) {
			try {
				soundURL.add(new File(filePath+"music\\" + list[i]).toURL());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** set music file name list */
	public void setSoundList() {
		soundList = new ArrayList<>();
		for(int i = 0 ; i < soundURL.size() ; i++) {
			String[] temp = soundURL.get(i).getPath().split("/"); 
			soundList.add(" " + (i+1) + ". " + temp[temp.length - 1].substring(0,temp[temp.length - 1].length() - 4));
		}
		musicList = MusicList.getInstance();
		musicList.setList(soundList.toArray());
	}
	
	/** get music list in /res/music/*.wav */
	private String[] getMusicList(String fileName) {
		File dir = new File(filePath+fileName);
		return getwavFile(dir);
	}

	/** get only .wav file safety */
	private String[] getwavFile(File dir) {
		List<String> list = new ArrayList<>();
		for (String file : dir.list())
			if (file.substring(file.length() - 4, file.length()).equals(".wav"))
				list.add(file);
		String[] fileList = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
			fileList[i] = list.get(i);
		return fileList;
	}

	/** add music file */
	@SuppressWarnings("deprecation")
	public void addMusic(File loadFile, File addFile) {
		boolean isWAV = loadFile.getName().substring(loadFile.getName().length() - 4,loadFile.getName().length()).equals(".wav");
		if(!isWAV) return;
		try {
			if(addFile.exists()) return;
			Files.copy(loadFile.toPath(), addFile.toPath());
			soundURL.add(addFile.toURL());
			soundList.add(addFile.getName().substring(0,addFile.getName().length() - 4));
			musicList.setList(soundList.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** delete music file */
	public void deleteMusic(int idx) {
		File deleteFile =new File(filePath + "music\\" + soundList.get(idx)+".wav");
		File saveFile = new File(filePath + "deleteMusic\\" + soundList.get(idx)+".wav");
		try {
			if(saveFile.exists())
				deleteFile.delete();
			else
				deleteFile.renameTo(saveFile);
			soundURL.remove(idx);
			soundList.remove(idx);
			musicList.setList(soundList.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** restore all music file */
	@SuppressWarnings("deprecation")
	public void restoreMusic() {
		String[] list = getMusicList("deleteMusic");
		for(int i = 0 ; i < list.length ; i++) {
			File restoreFile =new File(filePath + "deleteMusic\\" + list[i]);
			File addFile = new File(filePath + "music\\" + list[i]);
			try {
				restoreFile.renameTo(addFile);
				soundURL.add(addFile.toURL());
				soundList.add(list[i].substring(0,list[i].length() - 4));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		musicList.setList(soundList.toArray());
	}

	

}
