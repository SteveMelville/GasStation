package teamNorth;

public class Factory {
    //Creates a car based on the enumerated CarType passed into the Factory
    static Car carCreate(CarType type) {
        if (type == CarType.Car)
            return new Car();
        else if (type == CarType.PickupTruck)
            return new PickupTruck();
        else if (type == CarType.Sedan)
            return new Sedan();
        else if (type == CarType.SemiTruck)
            return new SemiTruck();
        else if (type == CarType.SportsCar)
            return new SportsCar();
        else if (type == CarType.SmallCar)
            return new SmallCar();
        else return null;
    }
}