package teamNorth;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class OrderSlider extends JFrame {

    private final JSlider slider;
    private final GridBagConstraints c;
    private Station station;

    public OrderSlider(GridBagConstraints c, Station station){
        this.c = c;
        this.station = station;

        slider = new JSlider(0,300,200);
        slider.setPreferredSize(new Dimension(150,30));
        slider.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent event){
                int value = slider.getValue();
                station.orderFuelLevel = value;
            }
        });
        c.gridx=4;
        c.gridy=3;
        c.ipadx=10;
        c.ipady=10;
        add(slider, c);
        setVisible(true);
    }
}
