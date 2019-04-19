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
    static double lostFuel, totalFuelSold, fuelExcess;
    int carsLost;

    public Station(){
        tank85 = Tank.getTank("85");
        tank89 = Tank.getTank("89");
        diesel = Tank.getTank("diesel");
        orderFuelLevel = Tank.getMaxFuel() / 2;
        lostFuel = 0;
        carsLost = 0;
        fuelExcess = 0;
        totalFuelSold = 0;
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
            tank85.refuelTank(Tank.getMaxFuel()-tank85.getFuelAmount());
        } else if (name == "89"){
            tank89.refuelTank(Tank.getMaxFuel()-tank89.getFuelAmount());
        }
    }

    public void carArrives() {
        CarType cartype = CarType.Car;
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

    public synchronized static void alertFuelExcess(double amount){ fuelExcess += amount; }

    public synchronized static void updateTotalFuelSold(double amount){
        totalFuelSold += amount;
    }

    public void carLeaves(int i){
        if(i >= 0 && i < size) pumps[i].setCar(null);
    }

    public int getCarsLost() {
        return carsLost;
    }

    public double getLostFuel() {
        return lostFuel;
    }

    public double getFuelExcess() {
        return fuelExcess;
    }
}
