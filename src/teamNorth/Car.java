package teamNorth;

public class Car {
    private double requestedFuel;
    static double MaxTankSize = 15;

    Car(double fuelAmount){
        requestedFuel = fuelAmount;
    }

    public double getRequestedFuel() {
        return requestedFuel;
    }
}
