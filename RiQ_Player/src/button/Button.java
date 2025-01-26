package button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import _main.Panel;
import music.SoundController;
import music.VolumeHandler;

@SuppressWarnings("serial")
public class Button extends JButton {

	private JButton play, stop, pause, before, next, suffle, volumeDown, volumeUp, timeDecrease, timeIncrease;
	private SoundController soundController;
	private VolumeHandler volumeH;
	
	private static Button instance;
	public static Button getInstance() {
		if(instance == null) instance = new Button();
		return instance;
	}

	private Button() {
		soundController = SoundController.getInstance();
		volumeH = VolumeHandler.getInstance();
		setNewButton();
		setButtonStyle();
		action();
	}
	
	/** create new button */
	private void setNewButton() {
		play = new JButton("▶️");
		stop = new JButton("■");
		pause = new JButton("| |");
		before = new JButton("<<");
		next = new JButton(">>");
		suffle = new JButton("Mix");
		timeDecrease = new JButton("-5s");
		timeIncrease = new JButton("+5s");
		volumeDown = new JButton("Vol-");
		volumeUp = new JButton("Vol+");
	}
	
	/** apply button style */
	private void setButtonStyle() {
		setStyle(play);
		setStyle(stop);
		setStyle(pause);
		setStyle(before);
		setStyle(next);
		setStyle(suffle);
		setStyle(volumeDown);
		setStyle(volumeUp);
		setStyle(timeDecrease);
		setStyle(timeIncrease);
	}
	
	/** button style setting */
	private void setStyle(JButton button) {
		button.setPreferredSize(new Dimension(80, 40));
		button.setForeground(Color.WHITE);
		button.setBackground(Color.BLACK);
		button.setBorderPainted(false);
		button.setFont(new Font("맑은 고딕",Font.BOLD,20));
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
	}
	
	/** add button to panel */
	public void addButton(Panel panel) {
		panel.add(before);
		panel.add(pause);
		panel.add(play);
		panel.add(stop);
		panel.add(next);
		panel.add(suffle);
		panel.add(timeDecrease);
		panel.add(timeIncrease);
		panel.add(volumeDown);
		panel.add(volumeUp);
	}

	/** button click event actions */
	private void action() {
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundController.play();
			}
		});
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundController.stop();
			}
		});
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundController.pause();
			}
		});
		before.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundController.before();
			}
		});
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundController.next();
			}
		});
		suffle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundController.suffle();
			}
		});
		volumeDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volumeH.volumeDown();
			}
		});
		volumeUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volumeH.volumeUp();
			}
		});
		timeDecrease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundController.timeDecrease();
			}
		});
		timeIncrease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundController.timeIncrease();
			}
		});

	}
}
