package music;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.sound.sampled.FloatControl;
import javax.swing.JLabel;

import _main.Panel;

public class VolumeHandler implements MouseListener, MouseMotionListener{

	private JLabel label;
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
		label = new JLabel("Vol-   ≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡●=======   Vol+");
		label.setForeground(Color.WHITE);
		label.setPreferredSize(new Dimension(340,25));
		label.setVerticalAlignment(JLabel.TOP);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.addMouseListener(this);
		label.addMouseMotionListener(this);
		panel.add(label);
	}
	
	/** draw volume line per (total play time / 25) */
	public void playTimeLine(int idx) {
		StringBuilder sb = new StringBuilder();
		sb.append("Vol-   ");
		for(int i = 0 ; i < 25 ; i++) {
			if(i < idx)
				sb.append("≡");
			else if ( i == idx)
				sb.append("●");
			else
				sb.append("=");
		}
		sb.append("   Vol+");
		label.setText(sb.toString());
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
		int idx = (int) ((49 + f) / 2);
		setGain(f);
		playTimeLine(idx);
	}
	
	// X : 80 ~ 260
	// gain : 0 ~ -50
	/** volume control by move volume bar */
	private void setVolume(MouseEvent e) {
		int x = e.getX();
		if(e.getX() < 80) x = 80;
		if(e.getX() > 260) x = 260;
		int idx = (int) ((x-80)*1.0 / 180 * 24);
		float volumeMove = (float) (-50.0 + (x-80)*1.0 / 179 * 50);
		if(volumeMove < -48)
			setGain((float) -80.0);
		else
			setGain(volumeMove);
		playTimeLine(idx);
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
}
