package music;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.aspose.imaging.ImageOptionsBase;
import com.aspose.imaging.Rectangle;
import com.aspose.imaging.imageoptions.PngOptions;

import _main.Panel;

public class ImageDAO {
	private JLabel label;

	public ImageDAO() {
	}
	private static ImageDAO instance;
	public static ImageDAO getInstance() {
		if(instance == null) instance = new ImageDAO();
		return instance;
	}
	
	/** music image setting */
	public void setImage(Panel panel) {
		if(label == null) {
			label = new JLabel();
			label.setBackground(Color.BLACK);
			label.setBorder(new LineBorder(Color.WHITE, 2, false));
			label.setPreferredSize(new Dimension(465, 296));
			label.setVerticalAlignment(JLabel.BOTTOM);
			label.setIcon(new ImageIcon(getImage()));
			label.setHorizontalAlignment(JLabel.CENTER);
			panel.add(label);
		}else
			label.setIcon(new ImageIcon(getImage(SoundController.getCount() - 1)));
	}
	
	/** transform image label visible */
	public void setVisionImage() {
		if(label.isVisible())
			label.setVisible(false);
		else
			label.setVisible(true);
	}

	/** get start image (noImage.png) */
	private Image getImage() {
		ImageIcon icon = new ImageIcon("res/image/noImage.png");
		Image img = icon.getImage().getScaledInstance(296, 296, Image.SCALE_SMOOTH);
		return img;
	}
	
	/** get save image.png from apple music search source image */
	private Image getImage(int idx) {
		String fileName = Sound.getInstance().getFileName(idx);
		String URL = String.format("https://music.apple.com/kr/search?term=%s",fileName.substring(4).replace(" - ", " ").replace(" ", "%20"));
		Image img = null;
		try {
			getAndSaveImage(URL);
			changeWebpToPng();
			img = new ImageIcon("res/image/image.png").getImage();
			return img.getScaledInstance(296, 343, Image.SCALE_AREA_AVERAGING);
		} catch (Exception e) {
			img = new ImageIcon("res/image/noImage.png").getImage();
		}
		return img.getScaledInstance(296, 296, Image.SCALE_AREA_AVERAGING);
	}
	
	/** get and save image  */
	private void getAndSaveImage(String imageUrl) throws Exception{
		Document doc = Jsoup.connect(imageUrl).timeout(3000).get();
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
		try (InputStream in = getURL(path).openStream(); OutputStream out = new FileOutputStream(Sound.getInstance().getFilePath() + "image\\image.webp");){
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
		try (com.aspose.imaging.Image image = com.aspose.imaging.Image.load(Sound.getInstance().getFilePath() + "image\\image.webp")){
			image.resize(296,343,com.aspose.imaging.ResizeType.LeftBottomToLeftBottom);
			ImageOptionsBase iob = new PngOptions();
			image.save(Sound.getInstance().getFilePath() + "image\\image.png",iob,Rectangle.getEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
