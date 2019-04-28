package teamNorth;
import javax.swing.*;
import java.awt.*;

public class TankDisplay extends JPanel implements Observer {
    Tank tank;
    GridBagConstraints c;
    private TankTextArea out;
    int id;
    Station station;
    Color color;

    TankDisplay(Tank tank, GridBagConstraints c, int id, Station station){
        //Initializes the class variables
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
        //Checks what level the tank is at and sets the color based on the tank fuel level
        if(tank.getFuelAmount() > Tank.orderFuelLevel){
            color = Color.green;
        } else if(tank.getFuelAmount() < Tank.orderFuelLevel && tank.getFuelAmount() > (Tank.getMaxFuel()/10)){
            color = Color.yellow;
        } else{
            color = Color.red;
        }
        //calls the setStatus method of the TankTextArea passing in the tank fuel amount, the tanks maxFuel, and the color
        out.setStatus(tank.getFuelAmount(), Tank.getMaxFuel(), color);

        //updates the TankString and sets it to the TankTextArea
        String TankString = tank.name +
                "\nCurrent Amount: " + String.format("%.2f", tank.fuelAmount) +
                "\nMax Capacity: " + Tank.maxFuel +
                "\nRefuel Level: " + String.format("%.2f", Tank.orderFuelLevel) +
                "\nOrder Placed: " + (tank.fuelOrdered ? "Y" : "N") +
                "\nOrder Quantity: " + Tank.maxFuel  +
                "\n                                                    ";
        setOutput(TankString);
    }

    //Setter and Getter for the TankTextArea output
    public void setOutput(String input){
        out.setText(input);
    }

    public String getOutput(){
        return out.getText();
    }
}