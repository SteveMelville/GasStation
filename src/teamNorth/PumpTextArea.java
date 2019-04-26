package teamNorth;

import javax.swing.*;
import java.awt.*;

public class PumpTextArea extends JTextArea {
    int status;
    public PumpTextArea(String s) {
        super(s);
        status = 0;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int w = this.getWidth(), h = this.getHeight();
        g.setColor(new Color(155,155,155,100));
        g.fillRect(0, h-status, w, status);
        g.setColor(Color.black);
    }

    public void setStatus(double requested, double pumped){
        status = (int) ((int) this.getHeight()*(pumped/requested));
    }
}
