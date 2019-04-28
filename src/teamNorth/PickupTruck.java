package teamNorth;
import java.util.Random;

public class PickupTruck extends Car{

    PickupTruck() {
        //Initializes the class variables
        setMaxTankSize(30.0);
        requestedFuel = random.nextInt((int)getMaxTankSize());
        if(requestedFuel == 0){
            requestedFuel = 1;
        }
        fuelType = "87";
        name = "TRUCK";
        image = " \nL___/T\\\n" +
                " o-----o";
    }
}