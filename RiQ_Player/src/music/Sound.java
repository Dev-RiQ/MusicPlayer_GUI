package music;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import list.MusicList;


public class Sound {

	private List<URL> soundURL;
	private List<String> soundList;

	public List<URL> getSoundURL() {
		return soundURL;
	}
	
	public int getListSIZE() {
		return soundURL.size();
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
		String[] list = getMusicList();
		String filePath = System.getProperty("user.dir") + "\\res\\music\\";
		soundURL = new ArrayList<>();
		for (int i = 0; i < list.length; i++) {
			try {
				soundURL.add(new File(filePath + list[i]).toURL());
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
			soundList.add(temp[temp.length - 1].substring(0,temp[temp.length - 1].length() - 4));
		}
		MusicList.getInstance().setList(soundList.toArray());
	}
	
	/** get music list in /res/music/*.wav */
	private String[] getMusicList() {
		String filePath = System.getProperty("user.dir") + "\\res\\music";
		File dir = new File(filePath);
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




}
