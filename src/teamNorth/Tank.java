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
        //Initialize the class variables
        fuelAmount = maxFuel;
        this.name = name;
        fuelOrdered = false;
    }

    public static Tank getTank(String name){
        //gets a tank based on the name entered, if there isn't a tank of the specified type already create one and then return it
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

        //Checks if the tank is empty
        if (fuelAmount == 0) {
            return 0;
        } else {
            //Checks if the amount needed is greater than what is in the tank
            if(fuelNeeded > fuelAmount){
                //sets the fuel to the amount in the tank and then empty the tank
                fuel = fuelAmount;
                fuelAmount -=fuelAmount;
            }
            else {
                //sets the fuel to what is needed and then decrement the tank by how much is needed
                fuel = fuelNeeded;
                fuelAmount = fuelAmount - fuelNeeded;
            }

            //returns the fuel
            return fuel;
        }
    }
    public void refuelTank(double fuel){
        //Checks if the amount in the tank plus the refuel amount is less than the max fuel amount
        if((fuelAmount + fuel) <= maxFuel) {
            //Adds the refuel amount to the tank and then alert the station that there has been no excess
            fuelAmount = fuelAmount + fuel;
            Station.alertFuelExcess(0, this);
        } else{
            //Sets the amount of excess, puts the max amount into the tank, and then alerts the station to the fuel excess
            fuelExcess = (fuelAmount + fuel) - maxFuel;
            fuelAmount = (fuelAmount + fuel) - fuelExcess;
            Station.alertFuelExcess(fuelExcess, this);
        }
    }

    //getters for the amount of fuel in the tank and the maxFuel
    public double getFuelAmount(){
        return fuelAmount;
    }

    public static double getMaxFuel() {
        return maxFuel;
    }
}