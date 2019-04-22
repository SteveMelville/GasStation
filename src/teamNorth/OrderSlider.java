package teamNorth;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class OrderSlider extends JPanel {

    JSlider slider;
    GridBagConstraints c;
    Station station;

    public OrderSlider(GridBagConstraints c, Station station){
        this.c = c;
        this.station = station;

        slider = new JSlider(0,300,200);
        slider.setPreferredSize(new Dimension(150,30));
        slider.addChangeListener(event -> {
            int value = slider.getValue();
            station.orderFuelLevel = value;
        });
        add(slider);
        setVisible(true);
    }
}
