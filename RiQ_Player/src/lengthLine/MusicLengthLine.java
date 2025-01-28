package lengthLine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;

import _main.Panel;
import music.SoundController;
import music.SoundInfo;

public class MusicLengthLine implements MouseListener, MouseMotionListener{

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
		line = new JLabel("●==============================================");
		panel.add(line);
		line.addMouseListener(this);
		line.addMouseMotionListener(this);
		line.setPreferredSize(new Dimension(360,13));
		line.setHorizontalAlignment(JLabel.CENTER);
		line.setForeground(Color.WHITE);
	}
	
	/** draw time line per (total play time / 47) */
	public void playTimeLine(int idx) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < 47 ; i++) {
			if(i < idx)
				sb.append("≡");
			else if ( i == idx)
				sb.append("●");
			else
				sb.append("=");
		}
		line.setText(sb.toString());
	}
	
	// X : 15 ~ 345
	/** time control by move time line */
	private void setTime(MouseEvent e) {
		int x = e.getX();
		if(e.getX() < 15) x = 15;
		if(e.getX() > 345) x = 345;
		int timeMove = (int) ((SoundInfo.getInstance().getPlayTime())*1.0 * ((x - 15)*1.0 / 330));
		SoundController.getInstance().timeSet(timeMove);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		setTime(e);
	}
	@Override
	public void mouseMoved(MouseEvent e) {
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
		setTime(e);
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
