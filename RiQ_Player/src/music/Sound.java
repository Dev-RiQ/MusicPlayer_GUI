package music;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Sound {

	private List<URL> soundURL;

	public List<URL> getSoundURL() {
		return soundURL;
	}
	
	public int getListSIZE() {
		return soundURL.size();
	}
	
	@SuppressWarnings("static-access")
	public String getURL_Path() {
		int count = SoundController.getInstance().getCount();
		count = count == 0? 0 : count -1;
		return soundURL.get(count).getPath();
	}

	private Sound() {
		String[] list = getMusicList();
		soundURL = new ArrayList<>();
		for (int i = 0; i < list.length; i++)
			soundURL.add(getClass().getResource("/music/" + list[i]));
	}
	private static Sound instance;
	public static Sound getInstance() {
		if(instance == null) instance = new Sound();
		return instance;
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
