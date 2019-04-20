package teamNorth;

import java.util.concurrent.Semaphore;

public class FuelTruck extends Thread {
    Tank tank;
    int orderArrivalTime = 5;
    Boolean fuelOrdered;
    Semaphore fuelOrder;

    FuelTruck(Tank tank, Semaphore sem) {
        this.tank = tank;
    }

    public void run() {
        while (true) {
            try {
                for (int i = 0; i < orderArrivalTime; i++) {
                    fuelOrder.acquire();
                }
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
