package teamNorth;

import java.util.Random;

public class Car implements ICar{
    private double requestedFuel;
    static private double MaxTankSize = 15;
    Random random = new Random();

    Car(){
        requestedFuel = random.nextInt((int)MaxTankSize);
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
