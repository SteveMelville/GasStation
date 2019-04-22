package teamNorth;

import javax.swing.*;
import java.awt.*;

public class StationDisplay extends JFrame{
    private PumpDisplay [] pumps;
    private TankDisplay [] tanks;
    private MainDisplay mainDisplay;
    private GridBagConstraints c;

    public StationDisplay(PumpDisplay [] pumps, TankDisplay [] tanks, MainDisplay mainDisplay, OrderSlider orderSlider, GridBagConstraints c){
        super("Text Copier");

        this.pumps = pumps;
        this.tanks = tanks;
        this.mainDisplay = mainDisplay;
        //initialize
        setLayout(new GridBagLayout());

        this.c = c;

        for(int i = 0; i < pumps.length; i++){
            c.gridx = i % 3;
            c.gridy = i < 3 ? 0 : i < 6 ? 1 : 2;
            c.ipadx = 10;
            c.ipady = 10;
            c.gridheight = 1;
            c.gridwidth = 1;
            add(pumps[i], c);
        }
        for(int i = 0; i < tanks.length; i++){
            c.gridx = 4 + i ;
            c.gridy = 0;
            c.ipadx = 10;
            c.ipady = 10;
            c.gridheight = 1;
            c.gridwidth = 1;
            add(tanks[i], c);
        }
        c.gridx=6;
        c.gridy=1;
        c.ipadx=10;
        c.ipady=10;
        add(orderSlider, c);
        setVisible(true);


        c.gridx = 4;
        c.gridy = 1;
        c.ipadx = 10;
        c.ipady = 10;
        c.gridheight = 1;
        c.gridwidth = 3;
        add(mainDisplay, c);

        setSize(2100, 1000);
        setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
        setVisible(true);
    }
}