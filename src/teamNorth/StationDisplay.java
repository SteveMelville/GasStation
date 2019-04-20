package teamNorth;

import javax.swing.*;
import java.awt.*;

public class StationDisplay extends JFrame{
    private PumpDisplay [] pumps;
    private TankDisplay [] tanks;
    private MainDisplay mainDisplay;
    private GridBagConstraints c;

    public StationDisplay(PumpDisplay [] pumps, TankDisplay [] tanks, MainDisplay mainDisplay, GridBagConstraints c){
        super("Text Copier");

        this.pumps = pumps;
        this.tanks = tanks;
        this.mainDisplay = mainDisplay;
        //initialize
        setLayout(new FlowLayout(FlowLayout.CENTER));

        this.c = c;

        for(int i = 0; i < pumps.length; i++){
            add(pumps[i]);
        }
        for(int i = 0; i < tanks.length; i++){
            add(tanks[i]);
        }
        add(mainDisplay);

        setSize(2000, 1000);
        setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
        setVisible(true);
    }
}