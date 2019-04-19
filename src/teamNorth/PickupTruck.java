package teamNorth;

import java.util.Random;

public class PickupTruck extends Car{
    String fuelType = "87";

    public String getFuelType(){
        return fuelType;
    }

    public void changeFuelType(){
        Random fuelChanger = new Random();

        switch(fuelChanger.nextInt(4)) {
            case 0: fuelType = "85";
                break;
            case 1: fuelType = "89";
                break;
            case 2: fuelType = "85"; //87 isn't implemented, 85 is placeholder.
                break;
            case 3: fuelType = "diesel";
                break;
            default:
                break;
        }
    }
}
