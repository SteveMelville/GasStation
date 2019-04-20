package teamNorth;

public class SportsCar extends Car {
    SportsCar(){
        setMaxTankSize(17.0);
        requestedFuel = random.nextInt((int)getMaxTankSize());
        fuelType = "89";
        name = "Sports Car";
    }
}
