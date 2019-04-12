package teamNorth;

public class Car {
    private double requestedFuel;

    Car(double fuelAmount){
        requestedFuel = fuelAmount;
    }

    public double getRequestedFuel() {
        return requestedFuel;
    }
}
