package teamNorth;

public class SportsCar extends Car {
    SportsCar(){
        //Initializes the class variables
        setMaxTankSize(17.0);
        requestedFuel = random.nextInt((int)getMaxTankSize());
        fuelType = "89";
        name = "Sports Car";
        image = "\n" +
                "\\_~___\n" +
                "O-----o\\";
    }
}