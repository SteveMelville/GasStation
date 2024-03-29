package teamNorth;
import javax.swing.*;
import java.awt.*;

public class PumpDisplay  extends JPanel implements Observer {
    Pump pump;
    GridBagConstraints c;
    private PumpTextArea out;
    int id;
    private Color top, bottom;

    PumpDisplay(Pump pump, GridBagConstraints c, int id){
        //Initializes the class variables
        this.pump = pump;
        this.c = c;
        this.id = id;
        setLayout(new GridBagLayout());
        out = new PumpTextArea("");
        add(out);
    }

    @Override
    public void update() {
        //sets the top color to red
        this.top = Color.red;
        //repaint();
        //revalidate();

        //If the pump isn't empty send the amount fuelAmount requested and the amount pumped to the pumpTextArea setStatus function else send 1 and 0
        if(pump.getCar() != null)
            out.setStatus(pump.getCar().getRequestedFuel(), pump.getAmountPumped());
        else
            out.setStatus(1,0);

        //Update the pumpStats string
        String pumpStats = "Pump " + (pump.getId() + 1) +
                "\n" + pump.getCarData() +
                "\nAmount Pumped: " + String.format("%.2f", pump.getAmountPumped()) +
                "\nPump Stats: \n -Regular: " + String.format("%.2f", pump.getGasRegularPumped())+
                "\n -Midgrade: " + String.format("%.2f", pump.getGasMidgradePumped()) +
                "\n -Premium: " + String.format("%.2f", pump.getGasPremiumPumped()) +
                "\n -Diesel: " + String.format("%.2f", pump.getDieselPumped()) +
                "\n                                                    ";

        //Outputs the string, sends the string to the PumpTextArea and then sets the pump display to true
        System.out.println(pumpStats);
        setOutput(pumpStats);
        setVisible(true);
    }

    //Getter and Setter for the PumpTextArea output
    public void setOutput(String input){
        out.setText(input);
    }

    public String getOutput(){
        return out.getText();
    }
}