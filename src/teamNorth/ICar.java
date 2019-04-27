package teamNorth;

//Interface for the CarTypes
public interface ICar {
    double getRequestedFuel();
    void setMaxTankSize(double maxTankSize);
    double getMaxTankSize();
    String getFuelType();
    String getName();
    String getImage();
}