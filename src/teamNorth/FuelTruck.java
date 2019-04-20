package teamNorth;

import java.util.concurrent.Semaphore;

public class FuelTruck extends Thread {
    Tank tank;
    int orderArrivalTime = 50;
    Boolean fuelOrdered;
    Semaphore fuelOrder;

    FuelTruck(Tank tank, Semaphore sem) {
        this.tank = tank;
        fuelOrder = sem;
        fuelOrdered = false;
    }

    public void run() {
        while (true) {
            try {
                for (int i = 0; i < orderArrivalTime; i++) {
                    fuelOrder.acquire();
                }
                System.out.println("Truck arrived! " + tank.name);
                tank.refuelTank(tank.maxFuel);
                fuelOrdered = false;
            }
            catch (Exception e){
                //This is a comment
            }
        }
    }

    public void setOrderArrivalTime(int orderArrivalTime) {
        this.orderArrivalTime = orderArrivalTime;
    }

    public void setFuelOrdered(Boolean fuelOrdered) {
        this.fuelOrdered = fuelOrdered;
    }
}
