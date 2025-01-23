package button;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import _main.Panel;
import music.SoundController;
import music.VolumeHandler;

@SuppressWarnings("serial")
public class Button extends JButton {

	private JButton play, stop, pause, before, next, suffle, volumeDown, volumeUp, volumeMin, volumeMax;
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
		setButtonSize();
		action();
	}
	
	/** create new button */
	private void setNewButton() {
		play = new JButton("▶️");
		stop = new JButton("■");
		pause = new JButton("| |");
		before = new JButton("<<");
		next = new JButton(">>");
		suffle = new JButton("⇄");
		volumeMin = new JButton("Min");
		volumeDown = new JButton("-");
		volumeUp = new JButton("+");
		volumeMax = new JButton("Max");
	}
	
	/** setting button size */
	private void setButtonSize() {
		play.setPreferredSize(new Dimension(80, 40));
		stop.setPreferredSize(new Dimension(80, 40));
		pause.setPreferredSize(new Dimension(80, 40));
		before.setPreferredSize(new Dimension(80, 40));
		next.setPreferredSize(new Dimension(80, 40));
		suffle.setPreferredSize(new Dimension(80, 40));
		volumeDown.setPreferredSize(new Dimension(80, 40));
		volumeUp.setPreferredSize(new Dimension(80, 40));
		volumeMin.setPreferredSize(new Dimension(80, 40));
		volumeMax.setPreferredSize(new Dimension(80, 40));
	}
	
	/** add button to panel */
	public void addButton(Panel panel) {
		panel.add(before);
		panel.add(pause);
		panel.add(play);
		panel.add(stop);
		panel.add(next);
		panel.add(suffle);
		panel.add(volumeMin);
		panel.add(volumeDown);
		panel.add(volumeUp);
		panel.add(volumeMax);
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
		volumeMin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volumeH.volumeMin();
			}
		});
		volumeMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volumeH.volumeMax();
			}
		});

	}
}
