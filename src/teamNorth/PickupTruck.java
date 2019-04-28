package teamNorth;
import java.util.Random;

public class PickupTruck extends Car{

    PickupTruck() {
        //Initializes the class variables
        setMaxTankSize(30.0);
        requestedFuel = random.nextInt((int)getMaxTankSize());
        fuelType = "87";
        name = "TRUCK";
        image = " \nL___/T\\\n" +
                " o-----o";
    }
}