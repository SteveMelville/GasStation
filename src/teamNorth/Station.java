package teamNorth;
import java.util.*;
import java.util.concurrent.Semaphore;

public class Station {
    int size = 9;
    Pump [] pumps = new Pump[size];
    Tank tank85;
    Tank tank89;
    Semaphore [] doWork = new Semaphore[size];
    Semaphore [] workDone = new Semaphore[size];
    boolean working;

    public Station(){
        tank85 = Tank.getTank("85");
        tank89 = Tank.getTank("89");

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
            int count = 0;
            while(working){

                for(int i = 0; i < size; i++){
                    if(pumps[i].isEmpty()) pumps[i].setCar(new Car(5));
                    doWork[i].release();
                }

                for(int i = 0; i < size; i++){
                    workDone[i].acquire();
                    if (count == 0) System.out.println("Pump " + pumps[i].getId() + ": Amount Pumped: " + pumps[i].getAmountPumped());
                }
                count++;
                if(count > 100){
                    count = 0;
                }
            }
        } catch(Exception e){
            return false;
        }
        return true;
    }
    
    public void TankReorder(String name){
        if (name == "85"){
            tank85.refuelTank(10000-tank85.getFuelAmount());
        } else if (name == "89"){
            tank89.refuelTank(10000-tank89.getFuelAmount());
        }
    }

    public void carArrives() {

        Car nextCar = new Car(Math.random()%Car.MaxTankSize);

        for(int i = 0; i < size; i++){
            if(pumps[i].isEmpty()) pumps[i].setCar(nextCar);
        }
    }

    public void carLeaves(int i){
        if(i >= 0 && i < size) pumps[i].setCar(null);
    }
}
