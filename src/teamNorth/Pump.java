package teamNorth;

public class Pump {
    private Tank tank85;
    private Tank tank89;
    private int id;
    private double amountPumped;
    private double pumpSpeed;
    private ICar car;

    Pump(int id){
        tank85 = Tank.getTank("85");
        tank89 = Tank.getTank("89");
        this.id = id;
        amountPumped = 0;
        pumpSpeed = 0.1;
        car = null;
    }

    public double pumpFuel(double amount, String name){
        double availableFuel = 0;
        amountPumped = 0;
        Tank tank = null;
        switch (name){
            case "85":  availableFuel = tank85.getFuelAmount();
                        if(availableFuel < amount) return HandleEmptyTank(tank85);
                        else tank = tank85;
                        break;
            case "89":  availableFuel = tank89.getFuelAmount();
                        if(availableFuel < amount) return HandleEmptyTank(tank89);
                        else tank = tank89;
                        break;
            case "87":  availableFuel = tank85.getFuelAmount();
                        if(availableFuel < amount / 2) return HandleEmptyTank(tank85);
                        availableFuel = tank89.getFuelAmount();
                        if(availableFuel < amount / 2) return HandleEmptyTank(tank89);
                        break;
        }
        if(tank != null)
            tank.fuelRequest(amount);
        else{
            pumpFuel(amount / 2, "85");
            pumpFuel(amount / 2, "89");
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

    public ICar getCar() {
        return car;
    }

    public void setCar(ICar car) {
        this.car = car;
    }

    public Boolean isEmpty(){
        if(car == null) { return true; }
        else { return false; }
    }
}
