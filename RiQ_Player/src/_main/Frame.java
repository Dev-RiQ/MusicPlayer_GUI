package _main;

import javax.swing.JFrame;

import button.Button;
import music.SoundInfo;

@SuppressWarnings("serial")
public class Frame extends JFrame{

	private Frame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("RiQ_Player");
	}
	private static Frame instance;
	public static Frame getInstance() {
		if(instance == null) instance = new Frame();
		return instance;
	}

	void run() {
		Panel panel = Panel.getInstance();
		add(panel);
		SoundInfo.getInstance().setMusicInfo(panel);
		Button.getInstance().addButton(panel);
		pack();

		setLocationRelativeTo(null);
		setVisible(true);

		panel.startThread();
	}
}
