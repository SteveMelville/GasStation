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
        double availableFuel = 0, availableFuel2 = 0;
        switch (name){
            case "85":  availableFuel = tank85.requestFuel(amount);
                        if(availableFuel < amount) return HandleEmptyTank(tank85);
                        break;
            case "89":  availableFuel = tank89.requestFuel(amount);
                        if(availableFuel < amount) return HandleEmptyTank(tank89);
                        break;
            case "87":  availableFuel = tank85.requestFuel(amount / 2);
                        if(availableFuel < amount / 2) return HandleEmptyTank(tank85);
                        availableFuel2 = tank89.requestFuel(amount / 2);
                        if(availableFuel2 < amount / 2) return HandleEmptyTank(tank89);
                        break;
        }

        while(amountPumped < amount){
            amountPumped += pumpSpeed;
        }

        amountPumped = 0;
        return amount;
    }

    public double HandleEmptyTank(Tank tank){
        return -1;
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
