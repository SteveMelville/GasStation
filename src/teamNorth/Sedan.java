package teamNorth;

public class Sedan extends Car {
    Sedan(){
        //Initializes the class variables
        setMaxTankSize(19.0);
        requestedFuel = random.nextInt((int)getMaxTankSize());
        fuelType = "85";
        name = "Sedan";
    }
}