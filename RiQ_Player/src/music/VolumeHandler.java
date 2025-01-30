package music;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.sound.sampled.FloatControl;
import javax.swing.JLabel;
import javax.swing.JSlider;

import _main.CustomSliderUI;
import _main.Frame;
import _main.Panel;

public class VolumeHandler implements MouseListener, MouseMotionListener, KeyListener{

	private JSlider slider;
	private JLabel label1;
	private JLabel label2;
	private FloatControl gain;
	private FloatControl setting;
	
	private static VolumeHandler instance;
	public static VolumeHandler getInstance() {
		if(instance == null) instance = new VolumeHandler();
		return instance;
	}

	VolumeHandler() {
		setGain((float) -14.0);
	}
	
	/** create volume controller */
	public void addVolumeCont(Panel panel) {
		slider = new JSlider(JSlider.HORIZONTAL,0,25,18);
		slider.setPreferredSize(new Dimension(250,13));
		slider.setBackground(Color.DARK_GRAY);
		slider.addMouseListener(this);
		slider.addMouseMotionListener(this);
		slider.addKeyListener(this);
		slider.setFocusable(false);
		slider.setUI(new CustomSliderUI(slider,250));
		label1 = new JLabel("Vol-");
		label2 = new JLabel("Vol+");
		setLabelStyle(label1);
		label1.setHorizontalAlignment(JLabel.RIGHT);
		setLabelStyle(label2);
		label2.setHorizontalAlignment(JLabel.LEFT);
		panel.add(label1);
		panel.add(slider);
		panel.add(label2);
	}
	
	private void setLabelStyle(JLabel label) {
		label.setPreferredSize(new Dimension(45,13));
		label.setFont(new Font("맑은 고딕",Font.BOLD, 15));
		label.setForeground(Color.WHITE);
	}
	
	public void playTimeLine(int value) {
		slider.setValue(value);
	}

	/** set volume gain */
	private void setGain(float dB) {
		gain = (FloatControl) SoundController.getInstance().getClip().getControl(FloatControl.Type.MASTER_GAIN);
		this.gain.setValue(dB);
		setting = gain;
	}

	/** maintain setting volume */
	public void volumeSetting() {
		setGain((float) setting.getValue());
	}
	
	public void setVolume(int num) {
		float f = (float) (setting.getValue());
		if(f == -80) f = -50;
		f += num * 2;
		if(f >= 0 || f <= -50) return;
		setGain(f);
		playTimeLine(slider.getValue() + num);
	}
	
	// X : 6 ~ 241
	// gain : 0 ~ -50
	/** volume control by move volume bar */
	private void setVolume(MouseEvent e) {
		double x = e.getX() * 1.0;
		if(x < 6) x = 6;
		if(x > 241) x = 241;
		x -= 6;
		x /= 235;
		x *= slider.getMaximum();
		float volumeMove = (float) (-50.0 + x*2.0);
		if(volumeMove < -48)
			setGain((float) -80.0);
		else
			setGain(volumeMove);
		playTimeLine((int) x);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setVolume(e);
	}
	@Override
	public void mouseMoved(MouseEvent e) {
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		setVolume(e);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		setVolume(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Frame.getInstance().keyPressed(e);
		e.consume();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Frame.getInstance().keyReleased(e);
		e.consume();
	}
}
