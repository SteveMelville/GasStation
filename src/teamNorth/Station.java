package teamNorth;
import java.awt.*;
import java.util.*;
import java.util.concurrent.Semaphore;
import static java.lang.Thread.sleep;

public class Station {
    private SliderDisplay slider;
    boolean working;
    boolean stationActive;
    static double fuelExcessRegular, fuelExcessDiesel, fuelExcessPremium, regularGallonsOrdered, premiumGallonsOrdered, dieselGallonsOrdered, regularGallonsDelivered, premiumGallonsDelivered, dieselGallonsDelivered;
    int carsLost, carsArrived, carsServed;
    static int regularTruckOrders, premiumTruckOrders, dieselTruckOrders, outOfRegular, outOfPremium, outOfDiesel, outOfMidgrade, CarChance = 10;
    static double premiumFuelSold, midgradeFuelSold, regularFuelSold, dieselFuelSold, lostFuel, totalFuelSold;
    int size = 9, speed;
    Tank tank85, tank89, diesel;
    Pump [] pumps = new Pump[size];
    FuelTruck truck85, truck89, truckDiesel;
    Semaphore [] doWork = new Semaphore[size], workDone = new Semaphore[size], orderFuel = new Semaphore[3];
    Observer [] pumpObservers = new Observer[9], tankObservers = new Observer[3];
    Observer mainStats;
    GridBagConstraints c;
    StationDisplay display;

    public Station(){
        //Initializes the class variables
        tank85 = Tank.getTank("85");
        tank89 = Tank.getTank("89");
        diesel = Tank.getTank("diesel");
        lostFuel = 0;
        carsLost = carsArrived = carsServed = 0;
        regularTruckOrders = premiumTruckOrders = dieselTruckOrders = 0;
        fuelExcessRegular = fuelExcessPremium = fuelExcessDiesel = 0;
        outOfDiesel = outOfPremium = outOfRegular = outOfMidgrade = 0;
        totalFuelSold = 0;
        premiumFuelSold = midgradeFuelSold = regularFuelSold = dieselFuelSold = regularGallonsOrdered = premiumGallonsOrdered = dieselGallonsOrdered = regularGallonsDelivered = premiumGallonsDelivered = dieselGallonsDelivered= 0;
        working = stationActive = true;
        speed = 50;

        for(int i = 0; i < 3; i++){
            orderFuel[i] = new Semaphore(0);
        }

        truck85 = new FuelTruck(tank85, orderFuel[0]);
        truck89 = new FuelTruck(tank89, orderFuel[1]);
        truckDiesel = new FuelTruck(diesel, orderFuel[2]);

        for(int i = 0; i < size; i++){
            doWork[i] = new Semaphore(0);
            workDone[i] = new Semaphore(0);
        }

        for(int i = 0; i < size; i++){
            pumps[i] = new Pump(i, doWork[i], workDone[i]);
        }

        c = new GridBagConstraints();

        tankObservers[0] = new TankDisplay(tank85, c, 0, this);
        tankObservers[1] = new TankDisplay(tank89, c, 1, this);
        tankObservers[2] = new TankDisplay(diesel, c, 2, this);

        for(int i = 0; i < pumpObservers.length; i++){
            pumpObservers[i] = new PumpDisplay(pumps[i], c, i);
        }

        mainStats = new MainDisplay(c, this);

        PumpDisplay [] tempPumpObservers = new PumpDisplay[pumpObservers.length];

        for(int i = 0; i < pumpObservers.length; i++){
            tempPumpObservers[i]= (PumpDisplay) pumpObservers[i];
        }

        TankDisplay [] tempTankObservers = new TankDisplay[tankObservers.length];

        for(int i = 0; i < tankObservers.length; i++){
            tempTankObservers[i]= (TankDisplay) tankObservers[i];
        }
        slider = new SliderDisplay(c, this);
        display = new StationDisplay(tempPumpObservers, tempTankObservers, (MainDisplay) mainStats, slider , c);
    }//THIS IS THE END OF THE CONSTRUCTOR, GO NO FARTHER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //k

    public boolean runStation(){
        try {
            //Starts the pump semaphores
            for(int i = 0; i < size; i++){
                pumps[i].start();
            }

            //Starts the truck semaphores
            truck85.start();
            truck89.start();
            truckDiesel.start();

            //sets the counts to 0
            int count = 0, count2 = 0;
            while(working){
                //IMPORTANT this print statement makes the start/stop button work... don't ask me why
                System.out.print("");

                //Checks if the station is active
                if(getStationActive()) {
                    var time = 0;
                    for (int i = 0; i < size; i++) {
                        doWork[i].release();
                        time += 1;
                        if(time == size)
                            break;
                    }

                    //calls the carArrives with the CarChance variable
                    carArrives(Station.CarChance);

                    for (int i = 0; i < size; i++) {
                        workDone[i].acquire();
                    }

                    //If statements to check if the tanks need to be refilled
                    if (tank85.getFuelAmount() < Tank.orderFuelLevel && !truck85.fuelOrdered) {
                        /*Outputs that the fuel type has been ordered sets teh fuelOrdered variables to true
                        and updates the gallons ordered and trucks ordered variable*/
                        System.out.println("85 ordered");
                        truck85.fuelOrdered = true;
                        tank85.fuelOrdered = true;
                        regularGallonsOrdered += (Tank.maxFuel - Tank.orderFuelLevel) + 50;
                        regularTruckOrders += 1;
                    }
                    if (tank89.getFuelAmount() < Tank.orderFuelLevel && !truck89.fuelOrdered) {
                        /*Outputs that the fuel type has been ordered sets teh fuelOrdered variables to true
                        and updates the gallons ordered and trucks ordered variable*/
                        System.out.println("89 ordered");
                        truck89.fuelOrdered = true;
                        tank89.fuelOrdered = true;
                        premiumGallonsOrdered += (Tank.maxFuel - Tank.orderFuelLevel) + 50;
                        premiumTruckOrders += 1;
                    }
                    if (diesel.getFuelAmount() < Tank.orderFuelLevel && !truckDiesel.fuelOrdered) {
                        /*Outputs that the fuel type has been ordered sets teh fuelOrdered variables to true
                        and updates the gallons ordered and trucks ordered variable*/
                        System.out.println("Diesel ordered");
                        truckDiesel.fuelOrdered = true;
                        diesel.fuelOrdered = true;
                        dieselGallonsOrdered += (Tank.maxFuel - Tank.orderFuelLevel) + 50;
                        dieselTruckOrders += 1;
                    }

                    //If the truck has been ordered release the truck semaphore
                    if (truck85.fuelOrdered) {
                        truck85.fuelOrder.release();
                    }
                    if (truck89.fuelOrdered) {
                        truck89.fuelOrder.release();
                    }
                    if (truckDiesel.fuelOrdered) {
                        truckDiesel.fuelOrder.release();
                    }
                    count++;

                    if (count > 1) {
                        //Update the pump, tank, and mainStats observers
                        for (int i = 0; i < pumpObservers.length; i++) {
                            pumpObservers[i].update();
                        }
                        for (int i = 0; i < tankObservers.length; i++) {
                            tankObservers[i].update();
                        }
                        mainStats.update();
                        count = 0;
                        count2++;
                    }
                    if (count2 > 10) {
                        count2 = 0;
                    }
                    sleep(speed);
                }
            }
        } catch(Exception e){
            return false;
        }
        return true;
    }

    public void carArrives(int chance) {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        if(random.nextInt(chance) == 0) {
            //Increments carsArrived and gets a random CarType, it then creates a car based on that CarType
            carsArrived += 1;
            CarType cartype = CarType.getRandomCar();
            ICar nextCar = Factory.carCreate(cartype);

            //checks for an empty pump and puts the car at the first one, if there isn't one increment the cars lost variable
            for (int i = 0; i < size; i++) {
                if (pumps[i].isEmpty()) {
                    pumps[i].setCar(nextCar);
                    break;
                }
                if (i == size - 1) {
                    carsLost++;
                }
            }
        }
    }

    public synchronized static void alertNotEnoughFuel(String type){
        //Alerts the station to the tank not having enough fuel and increment the variable matched on that type
        switch(type){
            case "85": outOfRegular++;
                break;
            case "89": outOfPremium++;
                break;
            case "87": outOfMidgrade++;
                break;
            case "diesel": outOfDiesel++;
                break;
        }
    }

    //Alerts the station to the amount of fuel excess
    public synchronized static void alertFuelExcess(double amount, Tank tank) {
        //Checks which tank is alerting it and increments that tanks excess and then increment the Gallons delivered for that tank
        if(tank.name == "Tank 85"){
            fuelExcessRegular += amount;
            regularGallonsDelivered += Tank.maxFuel - amount;
        } else if(tank.name == "Tank 89"){
            fuelExcessPremium += amount;
            premiumGallonsDelivered += Tank.maxFuel - amount;
        } else{
            fuelExcessDiesel += amount;
            dieselGallonsDelivered += Tank.maxFuel - amount;
        }
    }

    //Setter and Getter for the stationActive variable
    public void setStationActive(boolean status){
        stationActive = status;
    }
    public boolean getStationActive() {
        return stationActive;
    }

    //Updates the FuelSold variables
    public synchronized static void updateTotalFuelSold(double amount){
        totalFuelSold += amount;
    }
    public synchronized static void updatePremiumSold(double amount){
        premiumFuelSold += amount;
    }
    public synchronized static void updateMidgradeSold(double amount)  {midgradeFuelSold += amount;}
    public synchronized static void updateRegularSold(double amount){regularFuelSold += amount;}
    public synchronized static void updateDieselSold(double amount){dieselFuelSold += amount;}

    //Setters for the changeable variables
    public void setSimRunSpeed(int _speed){ speed = _speed; }

    public static void setCarChance(int _carChance){
        CarChance = _carChance;
    }

    public void setPumpSpeed(double newPumpSpeed){
        for(int i = 0; i < size; i++){
            pumps[i].setPumpSpeed(newPumpSpeed);
        }
    }
}