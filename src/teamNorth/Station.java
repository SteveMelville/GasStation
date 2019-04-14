package teamNorth;

public class Station {
    int size = 9;
    Pump [] pumps = new Pump[size];
    Tank tank85;
    Tank tank89;

    public Station(){
        tank85 = Tank.getTank("85");
        tank89 = Tank.getTank("89");
        for(int i = 0; i < size; i++){
            pumps[i] = new Pump(i);
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
    
    public void TankReorder(String name){
        if (name == "85"){
            tank85.refuelTank(10000-tank85.getFuelAmount());
        } else if (name == "89"){
            tank89.refuelTank(10000-tank89.getFuelAmount());
        }
    }
}
