package teamNorth;

public class Pump {
    private Tank tank85;
    private Tank tank89;
    private int id;
    private double amountPumped;
    private double pumpSpeed;

    Pump(int id){
        tank85 = Tank.getTank("85");
        tank89 = Tank.getTank("89");
        this.id = id;
        amountPumped = 0;
        pumpSpeed = 0.1;
    }

    public double pumpFuel(double amount, String name){
        while(amountPumped < amount){
            amountPumped += pumpSpeed;
        }

        amountPumped = 0;
        return amount;
    }

    public int getId() {
        return id;
    }

    public double getAmountPumped() {
        return amountPumped;
    }

    public void setPumpSpeed(double pumpSpeed) {
        this.pumpSpeed = pumpSpeed;
    }
}
