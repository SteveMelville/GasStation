package teamNorth;

import javax.swing.*;
import java.awt.*;

public class TankDisplay extends JPanel implements Observer {
    Tank tank;
    GridBagConstraints c;
    private JTextArea out;
    int id;
    Station station;
    FuelTruck truck;

    TankDisplay(Tank tank, GridBagConstraints c, int id, Station station){
        this.tank = tank;
        this.c = c;
        this.id = id;
        this.station = station;
        setLayout(new GridBagLayout());

        out = new JTextArea("");
        add(out);
    }

    @Override
    public void update() {

        String oof = tank.name +
                "\nCurrent Amount: " + String.format("%.2f", tank.fuelAmount) +
                "\nMax Capacity: " + Tank.maxFuel +
                "\nRefuel Level: " + String.format("%.2f", station.orderFuelLevel) +
                "\nOrder Placed: " + (tank.fuelOrdered ? "Y" : "N") +
                "\nOrder Quantity: " + Tank.maxFuel ;
        setOutput(oof);
    }

    public void setOutput(String input){
        out.setText(input);
    }

    public String getOutput(){
        return out.getText();
    }
}
