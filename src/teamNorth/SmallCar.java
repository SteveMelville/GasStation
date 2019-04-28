package teamNorth;

public class SmallCar extends Car{
    SmallCar(){
        //Initializes the class variables
        setMaxTankSize(2.0);
        requestedFuel = random.nextInt((int)getMaxTankSize());
        fuelType = "85";
        name = "Clown Car";
        image = "\n/T\\\n" +
                "o-o";
    }
}