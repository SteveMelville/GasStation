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
        this.pump = pump;
        this.c = c;
        this.id = id;
        setLayout(new GridBagLayout());

        out = new PumpTextArea("");
        add(out);
    }

    @Override
    public void update() {
        this.top = Color.red;
        //repaint();
        //revalidate();
        if(pump.getCar() != null)
            out.setStatus(pump.getCar().getRequestedFuel(), pump.getAmountPumped());
        else
            out.setStatus(1,0);
        String oof = "Pump " + (pump.getId() + 1) +
                "\n" + pump.getCarData() +
                "\nAmount Pumped: " + String.format("%.2f", pump.getAmountPumped()) +
                "\nPump Stats: \n -Regular: " + String.format("%.2f", pump.getGasRegularPumped())+
                "\n -Midgrade: " + String.format("%.2f", pump.getGasMidgradePumped()) +
                "\n -Premium: " + String.format("%.2f", pump.getGasPremiumPumped()) +
                "\n -Diesel: " + String.format("%.2f", pump.getDieselPumped()) +
                "\n                                                    ";

        System.out.println(oof);
        setOutput(oof);
        setVisible(true);
    }

    public void setOutput(String input){
        out.setText(input);
    }

    public String getOutput(){
        return out.getText();
    }
}
