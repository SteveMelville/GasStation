package teamNorth;

public class Tank {
    static Tank tank85;
    static Tank tank89;

    private Tank(){

    }

    public static Tank getTank(String name){
        if(name == "85"){
            if(tank85 == null) tank85 = new Tank();
            return tank85;
        }
        else if(name == "89"){
            if(tank89 == null) tank89 = new Tank();
            return tank89;
        }
        else return null;
    }
}
