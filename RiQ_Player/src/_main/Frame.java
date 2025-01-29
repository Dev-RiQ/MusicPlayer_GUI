package _main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import button.Button;
import lengthLine.MusicLengthLine;
import list.MusicList;
import music.ImageDAO;
import music.Sound;
import music.SoundController;
import music.SoundInfo;
import music.VolumeHandler;

@SuppressWarnings("serial")
public class Frame extends JFrame implements MouseListener, MouseMotionListener, KeyListener{

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
		panel.addKeyListener(this);
		panel.add(titleBar());
		panel.add(Button.getInstance().getExit());
		ImageDAO.getInstance().setImage(panel);
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
	
	private boolean isPressPlayOrPause, isPressBefore, isPressNext, isLeft, isRight, isUp, isDown, isSuffle, isList;

	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 32 && !isPressPlayOrPause) {
			isPressPlayOrPause = true;
			if(Button.getInstance().getPlay().getText().equals(" ▶️"))
				SoundController.getInstance().play();
			else
				SoundController.getInstance().pause();
		}
		if(e.getKeyCode() == 33 && !isPressBefore) {
			isPressBefore = true;
			SoundController.getInstance().before();
		}
		if(e.getKeyCode() == 34 && !isPressNext) {
			isPressNext = true;
			SoundController.getInstance().next();
		}
		if(e.getKeyCode() == 38 && !isUp) {
			isUp = true;
			VolumeHandler.getInstance().setVolume(1);
		}
		if(e.getKeyCode() == 40 && !isDown) {
			isDown = true;
			VolumeHandler.getInstance().setVolume(-1);
		}
		if(e.getKeyCode() == 37 && !isLeft) {
			isLeft = true;
			SoundController.getInstance().timeSet((int) Panel.getInstance().getCurTime() - 5);
		}
		if(e.getKeyCode() == 39 && !isRight) {
			isRight = true;
			SoundController.getInstance().timeSet((int) Panel.getInstance().getCurTime() + 5);
		}
		if(e.getKeyCode() == 82 && !isSuffle) {
			isSuffle = true;
			SoundController.getInstance().suffle();
		}
		if(e.getKeyCode() == 76 && !isList) {
			isList = true;
			MusicList.getInstance().setVisionPane();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 32) {
			isPressPlayOrPause = false;
		}
		if(e.getKeyCode() == 33) {
			isPressBefore = false;
		}
		if(e.getKeyCode() == 34) {
			isPressNext = false;
		}
		if(e.getKeyCode() == 38) {
			isUp = false;
		}
		if(e.getKeyCode() == 40) {
			isDown = false;
		}
		if(e.getKeyCode() == 37) {
			isLeft = false;
		}
		if(e.getKeyCode() == 39) {
			isRight = false;
		}
		if(e.getKeyCode() == 82) {
			isSuffle = false;
		}
		if(e.getKeyCode() == 76) {
			isList = false;
		}
	}
}
