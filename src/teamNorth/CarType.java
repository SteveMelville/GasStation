package teamNorth;
import java.util.Random;

public enum CarType {
    Car,PickupTruck,Sedan,SemiTruck,SportsCar,SmallCar;

    //Generates a random enumeration and returns the CarType generated
    public static CarType getRandomCar() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}