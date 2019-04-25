package teamNorth;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class SliderDisplay extends JPanel {

    JSlider slider;
    JSlider slider2;
    JSlider slider3;
    JSlider slider4;
    JButton button;
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
        JLabel status = new JLabel("Car Arrival Average Chance", JLabel.CENTER);

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

        slider2 = new JSlider(0,100,5);
        slider2.setPreferredSize(new Dimension(150,30));
        slider2.setMajorTickSpacing(25);
        slider2.setMinorTickSpacing(5);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);
        status = new JLabel("Delivery Time %", JLabel.CENTER);

// Add positions label in the slider
        position = new Hashtable();
        position.put(0, new JLabel("0"));
        position.put(25, new JLabel("25"));
        position.put(50, new JLabel("50"));
        position.put(75, new JLabel("75"));
        position.put(100, new JLabel("100"));
        slider2.addChangeListener(event -> {
            int value = slider2.getValue();
            FuelTruck.setOrderArrivalTime((value*10)+1);
        });
        add(status);
        add(slider2);

// Slider for changeable fuel pumping rate
// Set the label to be drawn
        slider3 = new JSlider(0, 100, 10);
        slider3.setPreferredSize(new Dimension(150, 30));
        slider3.setMajorTickSpacing(20);
        slider3.setMinorTickSpacing(10);
        slider3.setPaintTicks(true);
        slider3.setPaintLabels(true);
        status = new JLabel("Pump speed %", JLabel.CENTER);

// Add positions label in the slider
        position = new Hashtable();
        position.put(0 , new JLabel("0"));
        position.put(20, new JLabel("2"));
        position.put(40, new JLabel("4"));
        position.put(60 , new JLabel("6"));
        position.put(80, new JLabel("8"));
        position.put(100, new JLabel("10"));
        slider3.addChangeListener(event -> {
            int value = slider3.getValue();
            station.setPumpSpeed((double) value/100);
        });
        add(status);
        add(slider3);

// Slider for simulation run speed
// Set the label to be drawn
        slider4 = new JSlider(25, 325, 50);
        slider4.setPreferredSize(new Dimension(150, 30));
        slider4.setMajorTickSpacing(75);
        slider4.setMinorTickSpacing(25);
        slider4.setPaintTicks(true);
        slider4.setPaintLabels(true);
        status = new JLabel("Run speed (milliseconds per cycle):", JLabel.CENTER);

// Add positions label in the slider
        position = new Hashtable();
        position.put(25, new JLabel("25"));
        position.put(125, new JLabel("125"));
        position.put(225, new JLabel("225"));
        position.put(325, new JLabel("325"));
        slider4.addChangeListener(event -> {
            int value = slider4.getValue();
            station.setSimRunSpeed(value);
        });
        add(status);
        add(slider4);

        button = new JButton("Play/Pause");
        button.setBounds(50,100,95,30);
        button.addActionListener(e -> station.setStationActive(!station.getStationActive()));
        add(button);

        setVisible(true);
    }
}
