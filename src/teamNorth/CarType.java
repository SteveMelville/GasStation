package teamNorth;

import java.util.Random;

public enum CarType {
    Car,PickupTruck,Sedan,SemiTruck,SportsCar,SmallCar;

    public static CarType getRandomCar() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
