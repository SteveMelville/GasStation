package teamNorth;

import javax.swing.*;
import java.awt.*;

public class TankTextArea extends JTextArea {
    int status;
    Color color;
    public TankTextArea(String s) {
        super(s);
        status = 0;
        color = Color.green;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int w = this.getWidth(), h = this.getHeight();
        g.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),100));
        g.fillRect(0, h-status, w, h+status);
        g.setColor(Color.black);
    }

    public void setStatus(double maximum, double current, Color color){
        status = (int) ((int) this.getHeight()/(current/maximum));
        this.color = color;
    }
}
