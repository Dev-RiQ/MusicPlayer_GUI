package music;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.awt.Image;

import javax.swing.ImageIcon;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.aspose.imaging.ImageOptionsBase;
import com.aspose.imaging.Rectangle;
import com.aspose.imaging.imageoptions.PngOptions;

import list.MusicList;

public class Sound{

	private List<URL> soundURL;
	private List<String> soundList;
	private final String filePath = System.getProperty("user.dir") + "\\res\\";

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
			soundList.add(temp[temp.length - 1].substring(0,temp[temp.length - 1].length() - 4));
		}
		MusicList.getInstance().setList(soundList.toArray());
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
			MusicList.getInstance().setList(soundList.toArray());
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
			MusicList.getInstance().setList(soundList.toArray());
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
		MusicList.getInstance().setList(soundList.toArray());
	}

	/** get start image (noImage.png) */
	public Image getImage() {
		ImageIcon icon = new ImageIcon("res/image/noImage.png");
		Image img = icon.getImage().getScaledInstance(300, 250, Image.SCALE_SMOOTH);
		return img;
	}
	
	/** get save image.png from apple music search source image */
	public Image getImage(int idx) {
		String fileName = soundList.get(idx);
		String URL = String.format("https://music.apple.com/kr/search?term=%s",fileName.replace(" ", "%20"));
		Image img = null;
		try {
			getAndSaveImage(URL);
			changeWebpToPng();
			img = new ImageIcon("res/image/image.png").getImage();
		} catch (Exception e) {
			img = new ImageIcon("res/image/noImage.png").getImage();
		}
		return img.getScaledInstance(300, 300, Image.SCALE_AREA_AVERAGING);
	}
	
	/** get and save image  */
	private void getAndSaveImage(String imageUrl) throws Exception{
		Document doc = Jsoup.connect(imageUrl).get();
		String path = doc.select("source").get(0).attr("srcset");
		path = path.substring(0,path.indexOf(",") -19)+"296x296bf.webp";
		saveWebpFile(path);
	}
	
	/** get path URL */
	private URL getURL(String path) {
		URL url;
		try {
			url = new URL(path);
			return url;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** save .webp image file */
	private void saveWebpFile(String path) {
		try (InputStream in = getURL(path).openStream(); OutputStream out = new FileOutputStream(filePath + "image\\image.webp");){
			while(true) {
				int data = in.read();
				if(data == -1) break;
				out.write(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** change .webp file to .png */
	private void changeWebpToPng() {
		try (com.aspose.imaging.Image image = com.aspose.imaging.Image.load(filePath + "image\\image.webp")){
			ImageOptionsBase iob = new PngOptions();
			image.save(filePath + "image\\image.png",iob,Rectangle.getEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
