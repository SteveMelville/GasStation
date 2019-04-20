package teamNorth;

import java.util.concurrent.Semaphore;

public class Pump extends Thread {
    private int id;
    private ICar car;
    Semaphore doWork, workDone;
    private Tank tank85, tank89, diesel;
    private double amountPumped, pumpSpeed, gas85Pumped, gas87Pumped, gas89Pumped, dieselPumped;

    Pump(int id, Semaphore doWork, Semaphore workDone){
        this.doWork = doWork;
        this.workDone = workDone;
        tank85 = Tank.getTank("85");
        tank89 = Tank.getTank("89");
        diesel = Tank.getTank("diesel");
        this.id = id;

        amountPumped = 0;
        pumpSpeed = 0.2;
        gas85Pumped = 0;
        gas87Pumped = 0;
        gas89Pumped = 0;
        dieselPumped = 0;

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
                gas87Pumped += pumpSpeed;
            }
            else{
                amountPumped += tank.fuelRequest(pumpSpeed);
            }
            switch (name){
                case "85":  gas85Pumped += amountPumped;
                            Station.updateRegularSold(pumpSpeed);
                            break;
                case "89":  gas89Pumped += amountPumped;
                            Station.updatePremiumSold(pumpSpeed);
                            break;
                case "87":  gas87Pumped += pumpSpeed;
                            Station.updateMidgradeSold(pumpSpeed);
                            break;
                case "diesel":  dieselPumped += amountPumped;
                                Station.updateDieselSold(pumpSpeed);
                                break;
                default:    throw new Exception("Something went very wrong...");
            }

        }

        if (amountPumped < car.getRequestedFuel()){
            Station.alertNotEnoughFuel(car.getRequestedFuel() - amountPumped);
        }

        car = null;
        amountPumped = 0;
        return true;
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

    public double getGasRegularPumped() {
        return gas85Pumped;
    }

    public double getGasMidgradePumped() {
        return gas87Pumped;
    }

    public double getGasPremiumPumped() {
        return gas89Pumped;
    }

    public double getDieselPumped() {
        return dieselPumped;
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
