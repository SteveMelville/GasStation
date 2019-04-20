package teamNorth;

import javax.swing.*;
import java.awt.*;

public class TankDisplay extends JPanel implements Observer {
    Tank tank;
    GridBagConstraints c;
    private JTextArea out;
    int id;

    TankDisplay(Tank tank, GridBagConstraints c, int id){
        this.tank = tank;
        this.c = c;
        this.id = id;
        setLayout(new GridBagLayout());

        out = new JTextArea("");
        c.gridx= 4 + id ;
        c.gridy= 0;
        c.ipadx=10;
        c.ipady=10;
        add(out, c);
    }

    @Override
    public void update() {
        setOutput(tank.name);
    }

    public void setOutput(String input){
        out.setText(input);
    }

    public String getOutput(){
        return out.getText();
    }
}
