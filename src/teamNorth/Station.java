package teamNorth;

public class Station {
    Pump pump1;
    Pump pump2;
    Pump pump3;
    Tank tank85;
    Tank tank89;

    public Station(){
        tank85 = Tank.getTank("85");
        tank89 = Tank.getTank("89");
        pump1 = new Pump();
        pump2 = new Pump();
        pump3 = new Pump();
    }

    public double checkFuelLevel(){

        return 0;
    }

    public void fillTank(String Type){
        
    }

    public void pumpFuel(double amt, Pump whichPump, String Type){

    }
}
