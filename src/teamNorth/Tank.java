package teamNorth;

public class Tank {
    static Tank tank85;
    static Tank tank89;
    double fuelAmount;
    String name;
    private Tank(){
        fuelAmount = 10000.0;
        name = "Tank";
    }

    public static Tank getTank(String name){
        if(name == "85"){
            if(tank85 == null) tank85 = new Tank();
            return tank85;
        }
        else if(name == "89"){
            if(tank89 == null) tank89 = new Tank();
            return tank89;
        }
        else return null;
    }

    public double fuelRequest(double fuelNeeded) {
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
        if((fuelAmount + fuel) <= 10000) {
            fuelAmount = fuelAmount + fuel;
        } else{
            double fuelExcess = (fuelAmount + fuel) - 10000;
            fuelAmount = (fuelAmount + fuel) - fuelExcess;
        }
    }

    public double getFuelAmount(){
        return fuelAmount;
    }
}
