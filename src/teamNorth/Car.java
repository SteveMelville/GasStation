package teamNorth;
import java.util.Random;

public class Car implements ICar{
    double requestedFuel;
    static private double MaxTankSize = 15.0;
    String fuelType;
    String name;
    Random random = new Random();
    String image;

    Car(){
        //Initializes the class variables
        requestedFuel = random.nextInt((int)MaxTankSize);
        if(requestedFuel == 0){
            requestedFuel = 1;
        }
        fuelType = "85";
        name = "Car!";
        image = "\n _/T\\_\n" +
                " o---o";
    }

    //Getters and setters for the class variables
    public double getRequestedFuel() {
        return requestedFuel;
    }

    @Override
    public void setMaxTankSize(double maxTankSize) {
        MaxTankSize = maxTankSize;
    }

    @Override
    public double getMaxTankSize() {
        return MaxTankSize;
    }

    @Override
    public String getFuelType() { return fuelType; }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImage() {
        return image;
    }
}