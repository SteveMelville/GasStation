package teamNorth;

import javax.swing.*;
import java.awt.*;

public class MainDisplay extends JPanel implements Observer {
    GridBagConstraints c;
    private JTextArea out;
    Station station;

    MainDisplay(GridBagConstraints c, Station station){
        this.c = c;
        this.station = station;
        setLayout(new GridBagLayout());

        out = new JTextArea("");
        c.gridx=4;
        c.gridy=1;
        c.ipadx=10;
        c.ipady=10;
        add(out, c);
    }

    @Override
    public void update() {
        String oof = "\nTank 85: " + String.format("%.2f", station.tank85.getFuelAmount()) +
                "\nTank 89: " + String.format("%.2f", station.tank89.getFuelAmount()) +
                "\nTank Diesel: " + String.format("%.2f", station.diesel.getFuelAmount()) +
                "\nTotal Regular Sold: " + String.format("%.2f", station.regularFuelSold) +
                "\nTotal Midgrade Sold: " + String.format("%.2f", station.midgradeFuelSold) +
                "\nTotal Premium Sold: " + String.format("%.2f", station.premiumFuelSold) +
                "\nTotal Diesel Sold: " + String.format("%.2f", station.dieselFuelSold) +
                "\nCars lost: " + station.carsLost +
                "\nCars Arrived: " + station.carsArrived +
                "\nCars lost because out of -- Regular: " + station.outOfRegular +
                "\n   --Midgrade: " + station.outOfMidgrade +
                "\n   --Premium: " + station.outOfPremium +
                "\n   --Diesel: " + station.outOfDiesel +
                "\nExcess for -- Regular: " + String.format("%.2f", station.fuelExcessRegular) +
                "\n   -- Premium: " + String.format("%.2f", station.fuelExcessPremium) +
                "\n   -- Diesel: " + String.format("%.2f", station.fuelExcessDiesel);
        System.out.println(oof);
        setOutput(oof);
    }

    public void setOutput(String input){
        out.setText(input);
    }

    public String getOutput(){
        return out.getText();
    }
}
