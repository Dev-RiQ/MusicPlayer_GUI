package lengthLine;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

import _main.Panel;

public class MusicLengthLine {

	private JLabel line;

	private MusicLengthLine() {
	}
	private static MusicLengthLine instance;
	public static MusicLengthLine getInstance() {
		if(instance == null) instance = new MusicLengthLine();
		return instance;
	}
	
	/** initial time line settings */
	public void addLine(Panel panel) {
		line = new JLabel("●===========================================================");
		panel.add(line);
		line.setPreferredSize(new Dimension(500,13));
		line.setHorizontalAlignment(JLabel.CENTER);
		line.setForeground(Color.WHITE);
	}
	
	/** draw time line per (total play time / 60) */
	public void playTimeLine(int idx) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < 60 ; i++) {
			if(i < idx)
				sb.append("≡");
			else if ( i == idx)
				sb.append("●");
			else
				sb.append("=");
		}
		line.setText(sb.toString());
	}
	
	
}
