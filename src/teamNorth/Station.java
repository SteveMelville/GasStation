package teamNorth;
import java.util.*;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Station {
    boolean working;
    double orderFuelLevel;
    static double fuelExcessRegular, fuelExcessDiesel, fuelExcessPremium;
    int carsLost, carsArrived, carsServed;
    static int regularTruckOrders, premiumTruckOrders, dieselTruckOrders;
    static double premiumFuelSold, midgradeFuelSold, regularFuelSold, dieselFuelSold;
    int size = 9;
    Tank tank85, tank89, diesel;
    Pump [] pumps = new Pump[size];
    FuelTruck truck85, truck89, truckDiesel;
    static double lostFuel, totalFuelSold, fuelExcess;
    Semaphore [] doWork = new Semaphore[size], workDone = new Semaphore[size], orderFuel = new Semaphore[3];

    public Station(){
        tank85 = Tank.getTank("85");
        tank89 = Tank.getTank("89");
        diesel = Tank.getTank("diesel");
        orderFuelLevel = Tank.getMaxFuel() / 2;
        lostFuel = 0;
        carsLost = carsArrived = carsServed = 0;
        regularTruckOrders = premiumTruckOrders = dieselTruckOrders = 0;
        fuelExcessRegular = fuelExcessPremium = fuelExcessDiesel = 0;
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
    }

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
                    if (count == 0) System.out.println("Pump " + pumps[i].getId() + ": Amount Pumped: " + pumps[i].getAmountPumped() + pumps[i].getCarData());
                }

                if(tank85.getFuelAmount() < orderFuelLevel && !truck85.fuelOrdered){
                    System.out.println("85 ordered");
                    truck85.fuelOrdered = true;
                    regularTruckOrders += 1;
                }
                if(tank89.getFuelAmount() < orderFuelLevel && !truck89.fuelOrdered){
                    System.out.println("89 ordered");
                    truck89.fuelOrdered = true;
                    premiumTruckOrders += 1;
                }
                if(diesel.getFuelAmount() < orderFuelLevel && !truckDiesel.fuelOrdered){
                    System.out.println("Diesel ordered");
                    truckDiesel.fuelOrdered = true;
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
                if(count > 10){
                    System.out.println("Tank 85: " + tank85.getFuelAmount());
                    System.out.println("Tank 89: " + tank89.getFuelAmount());
                    System.out.println("Tank Diesel: " + diesel.getFuelAmount());
                    System.out.println("Total Regular Sold: " + regularFuelSold);
                    System.out.println("Total Midgrade Sold: " + midgradeFuelSold);
                    System.out.println("Total Premium Sold: " + premiumFuelSold);
                    System.out.println("Total Diesel Sold: " + dieselFuelSold);
                    count = 0;
                    count2++;
                }
                if(count2 > 10){
                    count2 = 0;
                    System.out.println("Cars lost: " + carsLost + "\nCars Arrived: " + carsArrived);
                }
                sleep(50);
            }
        } catch(Exception e){
            return false;
        }
        return true;
    }

    public void carArrives() {
        carsArrived += 1;
        CarType cartype = CarType.getRandomCar();
        ICar nextCar = Factory.carCreate(cartype);

        for(int i = 0; i < size; i++){
            if(pumps[i].isEmpty()) {
                pumps[i].setCar(nextCar);
                break;
            }
            if(i == size -1){
                carsLost++;
            }
        }
    }

    public synchronized static void alertNotEnoughFuel(double amount){
        lostFuel += amount;
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
