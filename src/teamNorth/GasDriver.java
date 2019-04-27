package teamNorth;

public class GasDriver {
    Station station;
    Boolean endOfTheWorld;

    GasDriver(){
        //Initiaizes the class variables
        station = new Station();
        endOfTheWorld = false;
    }

    //Runs the simulation
    Boolean run(){
        try {
            station.runStation();

            if (endOfTheWorld)
                throw new Exception();
        } catch(Exception e){
            return false;
        }
        return true;
    }
}