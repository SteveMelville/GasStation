package teamNorth;

public class Pump {
    Tank tank85;
    Tank tank89;

    Pump(){
        tank85 = Tank.getTank("85");
        tank89 = Tank.getTank("89");
    }

    double pumpFuel(double amount, String type){
        return amount;
    }
}
