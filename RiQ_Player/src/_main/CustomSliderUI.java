package _main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicSliderUI;

public class CustomSliderUI extends BasicSliderUI {

    private BasicStroke stroke = new BasicStroke(1f, BasicStroke.CAP_ROUND, 
            BasicStroke.JOIN_ROUND, 0f, new float[]{1f, 2f}, 0f);

    int y;
    public CustomSliderUI(JSlider b, int y) {
        super(b);
        this.y = y;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(11, 11);
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int x1 = thumbRect.x + 5;
        Stroke old = g2d.getStroke();
        g2d.setStroke(stroke);
        g2d.setPaint(Color.GRAY);
        g2d.fillRoundRect(0, 3, x1,5,5,5);
        if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
        	g2d.setPaint(Color.LIGHT_GRAY);
            g2d.fillRoundRect(x1, 3, y - x1,5,5,5);
        }
        g2d.setStroke(old);
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int x1 = thumbRect.x;
        int x2 = x1 + 1;
        g2d.setPaint(Color.LIGHT_GRAY);
        g2d.fillRoundRect(x1, 0, 11,11,100,100);
        g2d.setPaint(Color.DARK_GRAY);
        g2d.fillRoundRect(x2, 1, 9,9,100,100);
        Stroke old = g2d.getStroke();
        g2d.setStroke(new BasicStroke(2f));
        g2d.setStroke(old);
    }
}
