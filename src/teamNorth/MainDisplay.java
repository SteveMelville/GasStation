package teamNorth;
import javax.swing.*;
import java.awt.*;

public class MainDisplay extends JPanel implements Observer {
    GridBagConstraints c;
    private JTextArea out;
    Station station;

    MainDisplay(GridBagConstraints c, Station station){
        //Initializes the class variables
        this.c = c;
        this.station = station;
        setLayout(new GridBagLayout());

        out = new JTextArea("");
        add(out);
    }

    @Override
    public void update() {
        //Updates total cars lost
        int totalLost = station.carsLost + station.outOfDiesel + station.outOfPremium + station.outOfMidgrade + station.outOfRegular;

        //Updates the string to write to the Text area
        String oof = "Tank 85: " + String.format("%.2f", station.tank85.getFuelAmount()) +
                "\nTank 89: " + String.format("%.2f", station.tank89.getFuelAmount()) +
                "\nTank Diesel: " + String.format("%.2f", station.diesel.getFuelAmount()) +
                "\nTotal Regular Sold: " + String.format("%.2f", station.regularFuelSold) +
                "\nTotal Midgrade Sold: " + String.format("%.2f", station.midgradeFuelSold) +
                "\nTotal Premium Sold: " + String.format("%.2f", station.premiumFuelSold) +
                "\nTotal Diesel Sold: " + String.format("%.2f", station.dieselFuelSold) +
                "\n" +
                "\nCars Arrived: " + station.carsArrived +
                "\nTotal cars lost: " + totalLost +
                "\nCars lost because pumps full: " + station.carsLost +
                "\nCars lost because out of\n  -- Regular: " + station.outOfRegular +
                "\n   --Midgrade: " + station.outOfMidgrade +
                "\n   --Premium: " + station.outOfPremium +
                "\n   --Diesel: " + station.outOfDiesel +
                "\n" +
                "\nTotal fuel orders for" +
                "\n   -- Regular: " + station.regularTruckOrders +
                "\n   --Premium: " + station.premiumTruckOrders +
                "\n   --Diesel: " + station.dieselTruckOrders +
                "\nTotal gallons ordered for" +
                "\n   -- Regular: " + station.regularGallonsOrdered +
                "\n   --Premium: " + station.premiumGallonsOrdered +
                "\n   --Diesel: " + station.dieselGallonsOrdered +
                "\nTotal gallons delivered for" +
                "\n   -- Regular: " + String.format("%.2f", station.regularGallonsDelivered) +
                "\n   --Premium: " + String.format("%.2f", station.premiumGallonsDelivered) +
                "\n   --Diesel: " + String.format("%.2f", station.dieselGallonsDelivered) +
                "\nExcess for" +
                "\n  -- Regular: " + String.format("%.2f", station.fuelExcessRegular) +
                "\n   -- Premium: " + String.format("%.2f", station.fuelExcessPremium) +
                "\n   -- Diesel: " + String.format("%.2f", station.fuelExcessDiesel) +
                "\n                                                                ";
        //Prints the string and calls the setOutput function
        System.out.println(oof);
        setOutput(oof);
    }

    //setter and getter for the output text area
    public void setOutput(String input){
        //puts the given string into the text area
        out.setText(input);
    }

    public String getOutput(){
        return out.getText();
    }
}