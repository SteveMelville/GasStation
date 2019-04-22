package teamNorth;
import java.awt.*;
import java.util.*;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Station {
    boolean working;
    double orderFuelLevel;
    static double fuelExcessRegular, fuelExcessDiesel, fuelExcessPremium;
    int carsLost, carsArrived, carsServed;
    static int regularTruckOrders, premiumTruckOrders, dieselTruckOrders, outOfRegular, outOfPremium, outOfDiesel, outOfMidgrade;
    static double premiumFuelSold, midgradeFuelSold, regularFuelSold, dieselFuelSold;
    int size = 9;
    Tank tank85, tank89, diesel;
    Pump [] pumps = new Pump[size];
    FuelTruck truck85, truck89, truckDiesel;
    static double lostFuel, totalFuelSold;
    Semaphore [] doWork = new Semaphore[size], workDone = new Semaphore[size], orderFuel = new Semaphore[3];
    Observer [] pumpObservers = new Observer[9], tankObservers = new Observer[3];
    Observer mainStats;
    GridBagConstraints c;
    StationDisplay display;

    public Station(){
        tank85 = Tank.getTank("85");
        tank89 = Tank.getTank("89");
        diesel = Tank.getTank("diesel");
        orderFuelLevel = /*Tank.getMaxFuel() / 2*/ 200;
        lostFuel = 0;
        carsLost = carsArrived = carsServed = 0;
        regularTruckOrders = premiumTruckOrders = dieselTruckOrders = 0;
        fuelExcessRegular = fuelExcessPremium = fuelExcessDiesel = 0;
        outOfDiesel = outOfPremium = outOfRegular = outOfMidgrade = 0;
        totalFuelSold = 0;
        premiumFuelSold = midgradeFuelSold = regularFuelSold = dieselFuelSold = 0;
        working = true;

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

        display = new StationDisplay(tempPumpObservers, tempTankObservers, (MainDisplay) mainStats, c);
    }//THIS IS THE END OF THE CONSTRUCTOR, GO NO FARTHER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //k

    public double checkFuelLevel(String name){
        if (name == "85"){
            return tank85.getFuelAmount();
        } else if (name == "89"){
            return tank89.getFuelAmount();
        }else if (name == "diesel"){
            return diesel.getFuelAmount();
        }
        return 0;
    }

    public boolean runStation(){
        try {
            for(int i = 0; i < size; i++){
                pumps[i].start();
            }

            truck85.start();
            truck89.start();
            truckDiesel.start();

            int count = 0, count2 = 0;
            while(working){
                carArrives();
                for(int i = 0; i < size; i++){
                    doWork[i].release();
                }

                for(int i = 0; i < size; i++){
                    workDone[i].acquire();
                }

                if(tank85.getFuelAmount() < orderFuelLevel && !truck85.fuelOrdered){
                    System.out.println("85 ordered");
                    truck85.fuelOrdered = true;
                    tank85.fuelOrdered = true;
                    regularTruckOrders += 1;
                }
                if(tank89.getFuelAmount() < orderFuelLevel && !truck89.fuelOrdered){
                    System.out.println("89 ordered");
                    truck89.fuelOrdered = true;
                    tank89.fuelOrdered = true;
                    premiumTruckOrders += 1;
                }
                if(diesel.getFuelAmount() < orderFuelLevel && !truckDiesel.fuelOrdered){
                    System.out.println("Diesel ordered");
                    truckDiesel.fuelOrdered = true;
                    diesel.fuelOrdered = true;
                    dieselTruckOrders += 1;
                }

                if(truck85.fuelOrdered){
                    truck85.fuelOrder.release();
                }
                if(truck89.fuelOrdered){
                    truck89.fuelOrder.release();
                }
                if(truckDiesel.fuelOrdered){
                    truckDiesel.fuelOrder.release();
                }

                count++;
                if(count > 2){
                    for(int i = 0; i < pumpObservers.length; i++){
                        pumpObservers[i].update();
                    }
                    for(int i = 0; i < tankObservers.length; i++){
                        tankObservers[i].update();
                    }
                    mainStats.update();


                    count = 0;
                    count2++;
                }
                if(count2 > 10){
                    count2 = 0;
                }
                sleep(50);
            }
        } catch(Exception e){
            return false;
        }
        return true;
    }

    public void carArrives() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        if(random.nextInt(10) == 0) {
            carsArrived += 1;
            CarType cartype = CarType.getRandomCar();
            ICar nextCar = Factory.carCreate(cartype);

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

    public synchronized static void alertFuelExcess(double amount, Tank tank) {
        if(tank.name == "Tank 85"){
            fuelExcessRegular += amount;
        } else if(tank.name == "Tank 89"){
            fuelExcessPremium += amount;
        } else{
            fuelExcessDiesel += amount;
        }
    }

    public synchronized static void updateTotalFuelSold(double amount){
        totalFuelSold += amount;
    }
    public synchronized static void updatePremiumSold(double amount){
        premiumFuelSold += amount;
    }
    public synchronized static void updateMidgradeSold(double amount)  {midgradeFuelSold += amount;}
    public synchronized static void updateRegularSold(double amount){regularFuelSold += amount;}
    public synchronized static void updateDieselSold(double amount){dieselFuelSold += amount;}

    public void carLeaves(int i){
        if(i >= 0 && i < size) pumps[i].setCar(null);
    }

    public int getCarsLost() {
        return carsLost;
    }

    public double getLostFuel() {
        return lostFuel;
    }

    //public double getFuelExcess() {
        //return fuelExcess;
    //}

}
