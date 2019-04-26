package teamNorth;

import javax.swing.*;
import java.awt.*;

public class TankDisplay extends JPanel implements Observer {
    Tank tank;
    GridBagConstraints c;
    private TankTextArea out;
    int id;
    Station station;
    FuelTruck truck;
    Color color;

    TankDisplay(Tank tank, GridBagConstraints c, int id, Station station){
        this.tank = tank;
        this.c = c;
        this.id = id;
        this.station = station;
        setLayout(new GridBagLayout());
        color = Color.green;
        out = new TankTextArea("");
        add(out);
    }

    @Override
    public void update() {

        if(tank.getFuelAmount() > Tank.orderFuelLevel){
            color = Color.green;
        } else if(tank.getFuelAmount() < Tank.orderFuelLevel && tank.getFuelAmount() > (Tank.getMaxFuel()/10)){
            color = Color.yellow;
        } else{
            color = Color.red;
        }
        out.setStatus(tank.getFuelAmount(), Tank.getMaxFuel(), color);

        String oof = tank.name +
                "\nCurrent Amount: " + String.format("%.2f", tank.fuelAmount) +
                "\nMax Capacity: " + Tank.maxFuel +
                "\nRefuel Level: " + String.format("%.2f", Tank.orderFuelLevel) +
                "\nOrder Placed: " + (tank.fuelOrdered ? "Y" : "N") +
                "\nOrder Quantity: " + Tank.maxFuel  +
                "\n                                                    ";
        setOutput(oof);
    }

    public void setOutput(String input){
        out.setText(input);
    }

    public String getOutput(){
        return out.getText();
    }
}
