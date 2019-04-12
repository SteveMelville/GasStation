package teamNorth;

public class Pump {
    private Tank tank85;
    private Tank tank89;
    private int id;
    private double amountPumped;

    Pump(int id){
        tank85 = Tank.getTank("85");
        tank89 = Tank.getTank("89");
        this.id = id;
        amountPumped = 0;
    }

    public double pumpFuel(double amount, String name){
        return amount;
    }

    public int getId() {
        return id;
    }

    public double getAmountPumped() {
        return amountPumped;
    }
}
