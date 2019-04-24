package teamNorth;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Hashtable;

public class SliderDisplay extends JPanel {

    JSlider slider;
    GridBagConstraints c;
    Station station;

    public SliderDisplay(GridBagConstraints c, Station station){
        this.c = c;
        this.station = station;
        setLayout(new GridLayout(6,1));

// Slider for changeable car arrival chance
// Set the label to be drawn
        slider = new JSlider(1,100,10);
        slider.setPreferredSize(new Dimension(150,50));
        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        JLabel status = new JLabel("Car Arrival Average Chance Per Tic", JLabel.CENTER);

// Add positions label in the slider
        Hashtable position = new Hashtable();
        position.put(1, new JLabel("100"));
        position.put(25, new JLabel("75"));
        position.put(50, new JLabel("50"));
        position.put(75, new JLabel("25"));
        position.put(100, new JLabel("1"));

// Slider for changeable delivery time
// Set the label to be drawn
        slider.setLabelTable(position);
        slider.addChangeListener(event -> {
            int value = slider.getValue();
            Station.setCarChance(value);
        });
        add(status);
        add(slider);

        slider = new JSlider(1,1001,50);
        slider.setPreferredSize(new Dimension(150,30));
        slider.setMajorTickSpacing(250);
        slider.setMinorTickSpacing(50);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        status = new JLabel("Delivery Time in program tics", JLabel.CENTER);

// Add positions label in the slider
        position = new Hashtable();
        position.put(1, new JLabel("1"));
        position.put(250, new JLabel("250"));
        position.put(500, new JLabel("500"));
        position.put(750, new JLabel("750"));
        position.put(1000, new JLabel("1000"));
        slider.addChangeListener(event -> {
            int value = slider.getValue();
            FuelTruck.setOrderArrivalTime(value);
        });
        add(status);
        add(slider);

// Slider for changeable fuel pumping rate
// Set the label to be drawn
        slider = new JSlider(0, 10, 1);
        slider.setPreferredSize(new Dimension(150, 30));
        slider.setMajorTickSpacing(2);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        status = new JLabel("Pump speed in program tics", JLabel.CENTER);

// Add positions label in the slider
        position = new Hashtable();
        position.put(0 , new JLabel("0"));
        position.put(2, new JLabel("2"));
        position.put(4, new JLabel("4"));
        position.put(6 , new JLabel("6"));
        position.put(8, new JLabel("8"));
        position.put(10, new JLabel("10"));
        slider.addChangeListener(event -> {
            int value = slider.getValue();
            station.setPumpSpeed((double) value/10);
        });
        add(status);
        add(slider);

        setVisible(true);
    }
}
