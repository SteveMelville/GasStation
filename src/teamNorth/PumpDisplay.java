package teamNorth;

public class PumpDisplay implements Observer {
    Pump pump;

    PumpDisplay(Pump pump){
        this.pump = pump;
    }

    @Override
    public void update() {
        System.out.println("Pump " + pump.getId() + ": Amount Pumped: " + pump.getAmountPumped() + pump.getCarData());

    }
}
