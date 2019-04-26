package teamNorth;

public class Tank {
    static Tank tank85;
    static Tank tank89;
    static Tank tankDiesel;
    double fuelAmount;
    static double orderFuelLevel = 200;
    static double fuelExcess = 0;
    static double maxFuel = 300.0;
    boolean fuelOrdered;
    String name;
    private Tank(String name){
        fuelAmount = maxFuel;
        this.name = name;
        fuelOrdered = false;
    }

    public static Tank getTank(String name){
        if(name == "85"){
            if(tank85 == null) tank85 = new Tank("Tank 85");
            return tank85;
        }
        else if(name == "89"){
            if(tank89 == null) tank89 = new Tank("Tank 89");
            return tank89;
        }
        else if(name.toLowerCase() == "diesel"){
            if(tankDiesel == null) tankDiesel = new Tank("Tank Diesel");
            return tankDiesel;
        }
        else return null;
    }

    public synchronized double fuelRequest(double fuelNeeded) {
        double fuel = 0;

        if (fuelAmount == 0) {
            return 0;
        } else {
            if(fuelNeeded > fuelAmount){
                fuel = fuelAmount;
                fuelAmount -=fuelAmount;
            }
            else {
                fuel = fuelNeeded;
                fuelAmount = fuelAmount - fuelNeeded;
            }

            return fuel;
        }
    }
    public void refuelTank(double fuel){
        if((fuelAmount + fuel) <= maxFuel) {
            fuelAmount = fuelAmount + fuel;
            Station.alertFuelExcess(0, this);
        } else{
            fuelExcess = (fuelAmount + fuel) - maxFuel;
            fuelAmount = (fuelAmount + fuel) - fuelExcess;
            Station.alertFuelExcess(fuelExcess, this);
        }
    }

    public double getFuelAmount(){
        return fuelAmount;
    }

    public static double getMaxFuel() {
        return maxFuel;
    }

}
