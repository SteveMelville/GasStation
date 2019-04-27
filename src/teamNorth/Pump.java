package teamNorth;
import java.util.concurrent.Semaphore;

public class Pump extends Thread {
    private int id;
    private ICar car;
    Semaphore doWork, workDone;
    private Tank tank85, tank89, diesel;
    private double amountPumped, pumpSpeed, gas85Pumped, gas87Pumped, gas89Pumped, dieselPumped;

    Pump(int id, Semaphore doWork, Semaphore workDone){
        //Initializes the Class variables
        this.doWork = doWork;
        this.workDone = workDone;
        tank85 = Tank.getTank("85");
        tank89 = Tank.getTank("89");
        diesel = Tank.getTank("diesel");
        this.id = id;
        amountPumped = gas85Pumped = gas87Pumped = gas89Pumped = dieselPumped = 0;
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
        //sets the amount pumped on the tank to 0 and creates a tank variable
        amountPumped = 0;
        Tank tank = null;

        //Sets the tanks variable based on the name entered
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

        //loops while the requested fuel is greater than the amount pumped and the fuel level of the tank is greater than 0
        while(car.getRequestedFuel() > amountPumped && (tank == null ? tank85.fuelAmount - pumpSpeed / 2 > 0 && tank89.fuelAmount - pumpSpeed / 2 > 0 : tank.fuelAmount - pumpSpeed > 0)){
            workDone.release();
            doWork.acquire();
            /*If the tank specified is null get half of the requested fuel from each of the tanks(tank85 and tank89) and then increment gas87pumped
            else increment amount pumped*/
            if (tank == null) {
                amountPumped += tank85.fuelRequest(pumpSpeed / 2);
                amountPumped += tank89.fuelRequest(pumpSpeed / 2);
                gas87Pumped += pumpSpeed;
            }
            else{
                amountPumped += tank.fuelRequest(pumpSpeed);
            }

            //Increment the variable that matches the tank you pulled from based on the name entered and then alert the station to the amount sold
            switch (name){
                case "85":  gas85Pumped += pumpSpeed;
                            Station.updateRegularSold(pumpSpeed);
                            break;
                case "89":  gas89Pumped += pumpSpeed;
                            Station.updatePremiumSold(pumpSpeed);
                            break;
                case "87":  gas87Pumped += pumpSpeed;
                            Station.updateMidgradeSold(pumpSpeed);
                            break;
                case "diesel":  dieselPumped += pumpSpeed;
                                Station.updateDieselSold(pumpSpeed);
                                break;
                default:    throw new Exception("Something went very wrong...");
            }
        }

        //If the amount pumped is less than the amount requested the tank is empty and alert the station to not enough fuel of the given type
        if (amountPumped < car.getRequestedFuel()){
            Station.alertNotEnoughFuel(name);
        }

        //sets the car to null, amountPumped to zero and returns true
        car = null;
        amountPumped = 0;
        return true;
    }

    //Getters and setters for the Class variables
    public long getId() {
        return id;
    }
    public double getAmountPumped() {
        return amountPumped;
    }
    public ICar getCar() {
        return car;
    }
    public String getCarData(){
        //If the pump isn't empty return the present car date else return default string
        if(car != null)
            return car.getImage() + "\nCar:\n -Name: " + car.getName() + "\n -Fuel Type: " + car.getFuelType() + "\n -Amount Requested: " + car.getRequestedFuel();
        else
            return "\n\n\nCar:\n -Name: Oof \n -Fuel Type: Yeet \n -Amount Requested: no";
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

    public void setPumpSpeed(double pumpSpeed) {
        this.pumpSpeed = pumpSpeed;
    }
    public void setCar(ICar car) {
        this.car = car;
    }

    //Checks if the pumps is unoccupied
    public Boolean isEmpty(){
        if(car == null) { return true; }
        else { return false; }
    }
}