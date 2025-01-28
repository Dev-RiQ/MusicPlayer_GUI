package music;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import _main.Panel;

public class Image {
	private JLabel label;

	public Image() {
	}
	private static Image instance;
	public static Image getInstance() {
		if(instance == null) instance = new Image();
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
			label.setIcon(new ImageIcon(Sound.getInstance().getImage()));
			label.setHorizontalAlignment(JLabel.CENTER);
			panel.add(label);
		}else
			label.setIcon(new ImageIcon(Sound.getInstance().getImage(SoundController.getCount() - 1)));
	}
	
	/** transform image label visible */
	public void setVisionImage() {
		if(label.isVisible())
			label.setVisible(false);
		else
			label.setVisible(true);
	}
}
