package teamNorth;

import java.util.Random;

public class Car implements ICar{
    double requestedFuel;
    static private double MaxTankSize = 15.0;
    String fuelType;
    Random random = new Random();

    Car(){
        requestedFuel = random.nextInt((int)MaxTankSize);
        fuelType = "85";
    }

    public double getRequestedFuel() {
        return requestedFuel;
    }

    @Override
    public void setMaxTankSize(double maxTankSize) {
        MaxTankSize = maxTankSize;
    }

    @Override
    public double getMaxTankSize() {
        return MaxTankSize;
    }

    @Override
    public String getFuelType() {
        return fuelType;
    }
}
