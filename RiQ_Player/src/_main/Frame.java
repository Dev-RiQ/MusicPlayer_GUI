package _main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import button.Button;
import lengthLine.MusicLengthLine;
import music.Image;
import music.Sound;
import music.SoundInfo;
import music.VolumeHandler;

@SuppressWarnings("serial")
public class Frame extends JFrame implements MouseListener, MouseMotionListener{

	private Frame() {
		setUndecorated(true);
	}
	private static Frame instance;
	public static Frame getInstance() {
		if(instance == null) instance = new Frame();
		return instance;
	}

	void run() {
		Panel panel = Panel.getInstance();
		add(panel);
		panel.add(titleBar());
		panel.add(Button.getInstance().getExit());
		Image.getInstance().setImage(panel);
		SoundInfo.getInstance().setMusicInfo(panel);
		MusicLengthLine.getInstance().addLine(panel);
		Button.getInstance().addButton(panel);
		VolumeHandler.getInstance().addVolumeCont(panel);
		Sound.getInstance().setSoundList();
		pack();

		setLocationRelativeTo(null);
		setVisible(true);

		panel.startThread();
	}
	
	/** set new title bar */
	private Component titleBar() {
		JLabel label = new JLabel("  RiQ_Player");
		label.setBackground(Color.BLACK);
		label.setPreferredSize(new Dimension(290, 30));
		label.setHorizontalAlignment(JLabel.LEFT);
		label.setVerticalAlignment(JLabel.TOP);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		label.setForeground(Color.WHITE);
		label.addMouseListener(this);
		label.addMouseMotionListener(this);
		return label;
	}
	
	int x, y;

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getLocationOnScreen().x - this.getLocation().x;
		y = e.getLocationOnScreen().y - this.getLocation().y;
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
	public void mouseDragged(MouseEvent e) {
		this.setLocation(e.getLocationOnScreen().x - x,e.getLocationOnScreen().y - y);
	}
	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
