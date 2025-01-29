package music;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
		savePlayList("playList.txt", getPlayList());
		musicList.setList(soundList.toArray());
	}
	
	@SuppressWarnings("static-access")
	public void moveList(int idx, int dir) {
		if(dir == -1 && idx == 0) return;
		if(dir == 1 && idx == soundURL.size() -1) return;
		URL tempURL = soundURL.get(idx);
		soundURL.set(idx, soundURL.get(idx + dir));
		soundURL.set(idx + dir, tempURL);
		String temp = soundList.get(idx);
		soundList.set(idx, soundList.get(idx + dir));
		soundList.set(idx + dir, temp);
		int count = SoundController.getInstance().getCount();
		if(count == idx + 1)
			SoundController.getInstance().setCount(count + dir);
		saveAndSetPlayList();
	}
	
	/** get music list in /res/music/*.wav */
	private String[] getMusicList(String fileName) {
		File dir = new File(filePath+fileName);
		return getwavFile(dir);
	}

	/** get only .wav file safety */
	private String[] getwavFile(File dir) {
		String playList = loadPlayList("playList.txt");
		if(playList == null) {
			List<String> list = new ArrayList<>();
			for (String file : dir.list())
				if (file.substring(file.length() - 4, file.length()).equals(".wav"))
					list.add(file);
			String[] fileList = new String[list.size()];
			for (int i = 0; i < list.size(); i++)
				fileList[i] = list.get(i);
			return fileList;
		}
		return playList.split("\n");
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
			saveAndSetPlayList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** delete music in list */
	public void deleteMusic(int idx) {
		StringBuilder sb = new StringBuilder();
		if(loadPlayList("deleteList.txt") == null)
			sb.append(soundList.get(idx).substring(soundList.get(idx).indexOf(".") + 2) + ".wav");
		else
			sb.append(loadPlayList("deleteList.txt") + "\n" + soundList.get(idx).substring(soundList.get(idx).indexOf(".") + 2) + ".wav");
		soundURL.remove(idx);
		savePlayList("deleteList.txt", sb.toString());
		saveAndSetPlayList();
	}
	
	/** restore all music list */
	@SuppressWarnings("deprecation")
	public void restoreMusic() {
		if(loadPlayList("deleteList.txt") == null) return;
		String[] list = loadPlayList("deleteList.txt").split("\n");
		for(int i = 0 ; i < list.length ; i++) {
			File restoreFile =new File(filePath + "music\\" + list[i]);
			try {
				soundURL.add(restoreFile.toURL());
				soundList.add(list[i].substring(0,list[i].length() - 4));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		savePlayList("deleteList.txt","");
		saveAndSetPlayList();
	}
	
	private void saveAndSetPlayList() {
		setSoundList();
		savePlayList("playList.txt", getPlayList());
		musicList.setList(soundList.toArray());
	}
	
	/** get now play list */
	public String getPlayList() {
		StringBuilder sb = new StringBuilder();
		for(String list : soundList)
			sb.append(list.substring(list.indexOf(".")+2) + ".wav\n");
		return sb.toString().substring(0, sb.length() - 1);
	}

	/** save now play list */
	public void savePlayList(String fileName, String data) {
		try (FileWriter fw = new FileWriter(filePath + "playList\\" + fileName)){
			fw.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** get load play list */
	private String loadPlayList(String fileName) {
		StringBuilder sb = new StringBuilder();
		try (FileReader fr = new FileReader(filePath + "playList\\" + fileName); BufferedReader br = new BufferedReader(fr)){
			while(true) {
				String temp = br.readLine();
				if(temp == null) break;
				sb.append(temp + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString().length() == 0? null : sb.toString().substring(0,sb.length()-1);
	}

}
