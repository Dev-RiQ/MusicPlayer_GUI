package lengthLine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JSlider;

import _main.Frame;
import _main.Panel;
import music.SoundController;

public class MusicLengthLine implements MouseListener, MouseMotionListener, KeyListener{

	private JSlider slider;

	private MusicLengthLine() {
	}
	private static MusicLengthLine instance;
	public static MusicLengthLine getInstance() {
		if(instance == null) instance = new MusicLengthLine();
		return instance;
	}
	
	/** initial time line settings */
	public void addLine(Panel panel) {
		slider = new JSlider(JSlider.HORIZONTAL,0,100,0);
		slider.setPreferredSize(new Dimension(340,13));
		slider.setBackground(Color.DARK_GRAY);
		slider.addMouseListener(this);
		slider.addMouseMotionListener(this);
		slider.addKeyListener(this);
		panel.add(slider);
	}
	
	public void setMaximum(int time) {
		slider.setMaximum(time);
	}
	
	public void setValue(int time) {
		slider.setValue(time);
	}
	
	public double getValue() {
		return (double) slider.getValue();
	}
	
	public void playTimeLine(int curTime) {
		slider.setValue(curTime);
	}
	
	public void moveTimeLine(int curTime) {
		slider.setValue(curTime);
	}
	
	// X : 8 ~ 330
	/** time control by move time line */
	private void setTime(MouseEvent e) {
		if(!SoundController.getInstance().isRun()) {
			slider.setValue(0);
			return;
		}
		double value = e.getX() * 1.0;
		if(value < 8) value = 8;
		if(value > 330) value = 330;
		value -= 8;
		value /= 322;
		value *= slider.getMaximum();
		SoundController.getInstance().timeSet(slider.getValue());
		Panel.getInstance().setCurTime(value);
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
		setTime(e);
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
