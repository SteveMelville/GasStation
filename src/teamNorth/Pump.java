package teamNorth;

import java.util.concurrent.Semaphore;

public class Pump extends Thread {
    private Tank tank85;
    private Tank tank89;
    private Tank diesel;
    private int id;
    private double amountPumped;
    private double pumpSpeed;
    Semaphore doWork, workDone;
    private ICar car;

    Pump(int id, Semaphore doWork, Semaphore workDone){
        this.doWork = doWork;
        this.workDone = workDone;
        tank85 = Tank.getTank("85");
        tank89 = Tank.getTank("89");
        diesel = Tank.getTank("diesel");
        this.id = id;
        amountPumped = 0;
        pumpSpeed = 0.2;
        car = null;
    }

    public void run(){
        while(true) {
            try {
                doWork.acquire();
                if (isEmpty()) {
                    //doNothing
                } else {
                    pumpFuel(car.getFuelType());
                }
                workDone.release();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean pumpFuel(String name) throws Exception{
        double availableFuel = 0;
        amountPumped = 0;
        Tank tank = null;
        switch (name){
            case "85":  tank = tank85;
                        break;
            case "89":  tank = tank89;
                        break;
            case "87":  //Nothing right now
                        break;
            case "diesel": tank = diesel;
                            break;
            default:    car = null;
                        return false;
        }
        while(car.getRequestedFuel() > amountPumped && (tank == null ? tank85.fuelAmount - pumpSpeed / 2 > 0 && tank89.fuelAmount - pumpSpeed / 2 > 0 : tank.fuelAmount - pumpSpeed > 0)){
            workDone.release();
            doWork.acquire();
            if (tank == null) {
                amountPumped += tank85.fuelRequest(pumpSpeed / 2);
                amountPumped += tank89.fuelRequest(pumpSpeed / 2);
            }
            else{
                amountPumped += tank.fuelRequest(pumpSpeed);
            }
            Station.updateTotalFuelSold(pumpSpeed);
        }
        if (amountPumped < car.getRequestedFuel()){
            Station.alertNotEnoughFuel(car.getRequestedFuel() - amountPumped);
        }
        car = null;
        amountPumped = 0;
        return true;
    }

    public double HandleEmptyTank(Tank tank){
        return -1;
    }

    public long getId() {
        return id;
    }

    public double getAmountPumped() {
        return amountPumped;
    }

    public void setPumpSpeed(double pumpSpeed) {
        this.pumpSpeed = pumpSpeed;
    }

    public ICar getCar() {
        return car;
    }

    public void setCar(ICar car) {
        this.car = car;
    }

    public Boolean isEmpty(){
        if(car == null) { return true; }
        else { return false; }
    }
}
