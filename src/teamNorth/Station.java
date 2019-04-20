package teamNorth;
import java.util.*;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Station {
    int size = 9;
    Pump [] pumps = new Pump[size];
    Tank tank85;
    Tank tank89;
    Tank diesel;
    Semaphore [] doWork = new Semaphore[size];
    Semaphore [] workDone = new Semaphore[size];
    boolean working;
    double orderFuelLevel;
    static double lostFuel, totalFuelSold;
    static double fuelExcessRegular, fuelExcessDiesel, fuelExcessPremium;
    int carsLost, carsArrived, carsServed;
    int regularTruckOrders, premiumTruckOrders, dieselTruckOrders;
    static double premiumFuelSold, midgradeFuelSold, regularFuelSold, dieselFuelSold;

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
            int count = 0, count2 = 0;
            while(working){
                System.out.println("Tank 1: " + tank85.getFuelAmount());
                System.out.println("Total Fuel Sold: " + totalFuelSold);
                carArrives();
                for(int i = 0; i < size; i++){
                    doWork[i].release();
                }

                for(int i = 0; i < size; i++){
                    workDone[i].acquire();
                    if (count == 0) System.out.println("Pump " + pumps[i].getId() + ": Amount Pumped: " + pumps[i].getAmountPumped());
                }

                if(tank85.getFuelAmount() < orderFuelLevel){
                    TankReorder("85");
                }
                if(tank89.getFuelAmount() < orderFuelLevel){
                    TankReorder("89");
                }
                if(diesel.getFuelAmount() < orderFuelLevel){
                    TankReorder("diesel");
                }
                count++;
                if(count > 1){
                    count = 0;
                    count2++;
                }
                if(count2 > 10){
                    //working = false;
                }
                sleep(500);
            }
        } catch(Exception e){
            return false;
        }
        return true;
    }

    public void TankReorder(String name){
        if (name == "85"){
            regularTruckOrders += 1;
            tank85.refuelTank(Tank.getMaxFuel()-tank85.getFuelAmount());
        } else if (name == "89"){
            premiumTruckOrders += 1;
            tank89.refuelTank(Tank.getMaxFuel()-tank89.getFuelAmount());
        } else if (name == "diesel"){
            dieselTruckOrders += 1;
            diesel.refuelTank(Tank.getMaxFuel()-diesel.getFuelAmount());
        }
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
