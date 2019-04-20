package teamNorth;

public class SmallCar extends Car{
    SmallCar(){
        setMaxTankSize(2.0);
        requestedFuel = random.nextInt((int)getMaxTankSize());
        fuelType = "85";
        name = "Clown Car";
    }
}
