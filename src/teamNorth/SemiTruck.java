package teamNorth;

public class SemiTruck extends Car {
    SemiTruck(){
        //Initializes the class variables
        setMaxTankSize(63.0);
        requestedFuel = random.nextInt((int)getMaxTankSize());
        if(requestedFuel == 0){
            requestedFuel = 5;
        }
        fuelType = "diesel";
        name = "Semi-Truck";
        image = " _________\n" +
                "|_________|_|T_\n" +
                "OO                OO O ";
    }
}