package teamNorth;

public class GasDriver {
    Station station;
    Boolean endOfTheWorld;

    GasDriver(){
        station = new Station();
        endOfTheWorld = false;
    }

    Boolean run(){
        try {
            if (endOfTheWorld)
                throw new Exception();
        } catch(Exception e){
            return false;
        }
        return true;
    }
}
