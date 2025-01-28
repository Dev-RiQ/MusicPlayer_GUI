package button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JButton;

import _main.Panel;
import list.MusicList;
import music.SoundController;

@SuppressWarnings("serial")
public class Button extends JButton {

	private JButton play, before, next, suffle, exit, list;
	private SoundController soundController;
	
	private static Button instance;
	public static Button getInstance() {
		if(instance == null) instance = new Button();
		return instance;
	}

	private Button() {
		soundController = SoundController.getInstance();
		setNewButton();
		setButtonStyle();
		action();
	}
	
	/** create new button */
	private void setNewButton() {
		play = new JButton(" ▶️");
		before = new JButton("◀◀");
		next = new JButton("▶️▶️");
		suffle = new JButton("∝");
		exit = new JButton("Ⅹ");
		list = new JButton("iii");
	}
	
	/** get title exit button */
	public Component getExit() {
		setStyle(exit);
		exit.setFont(new Font("맑은 고딕",Font.BOLD,20));
		exit.setPreferredSize(new Dimension(55, 30));
		exit.setVerticalAlignment(JButton.BOTTOM);
		return exit;
	}
	
	/** apply button style */
	private void setButtonStyle() {
		setStyle(play);
		setStyle(before);
		setStyle(next);
		setStyle(suffle);
		setStyle(list);
		play.setFont(new Font("맑은 고딕",Font.BOLD,30));
		before.setForeground(Color.LIGHT_GRAY);
		next.setForeground(Color.LIGHT_GRAY);
		suffle.setFont(new Font("맑은 고딕",Font.BOLD,20));
		AffineTransform rotate = AffineTransform.getRotateInstance((-0.5)*Math.PI);
		list.setFont(list.getFont().deriveFont(rotate));
		suffle.setPreferredSize(new Dimension(110, 45));
		list.setPreferredSize(new Dimension(110, 23));
		list.setVerticalAlignment(JButton.BOTTOM);
	}
	
	/** button style setting */
	private void setStyle(JButton button) {
		button.setPreferredSize(new Dimension(100, 45));
		button.setForeground(Color.WHITE);
		button.setBackground(Color.BLACK);
		button.setBorderPainted(false);
		button.setFont(new Font("맑은 고딕",Font.BOLD,15));
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
	}
	
	/** add button to panel */
	public void addButton(Panel panel) {
		panel.add(before);
		panel.add(play);
		panel.add(next);
		panel.add(suffle);
		panel.add(list);
	}
	
	/** transform play button text */
	public void setPlayButton(boolean isPlay) {
		if(isPlay)
			play.setText("| |");
		else
			play.setText(" ▶️");
	}

	/** button click event actions */
	private void action() {
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(play.getText().equals(" ▶️"))
					soundController.play();
				else
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
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel.getInstance().endThread();
			}
		});
		list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MusicList.getInstance().setVisionPane();
			}
		});

	}
}
