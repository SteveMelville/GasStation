package teamNorth;

public class Sedan extends Car {
    Sedan(){
        //Initializes the class variables
        setMaxTankSize(19.0);
        requestedFuel = random.nextInt((int)getMaxTankSize());
        if(requestedFuel == 0){
            requestedFuel = 1;
        }
        fuelType = "85";
        name = "Sedan";
    }
}