package teamNorth;

public class SemiTruck extends Car {
    SemiTruck(){
        setMaxTankSize(63.0);
        requestedFuel = random.nextInt((int)getMaxTankSize());
        fuelType = "diesel";
        name = "Semi-Truck";
    }

}
