package teamNorth;

public class Car implements ICar{
    private double requestedFuel;
    static private double MaxTankSize = 15;

    Car(double fuelAmount){
        requestedFuel = fuelAmount;
    }

    public double getRequestedFuel() {
        return requestedFuel;
    }

    @Override
    public void setMaxTankSize(int maxTankSize) {
        MaxTankSize = maxTankSize;
    }

    @Override
    public double getMaxTankSize() {
        return MaxTankSize;
    }
}
