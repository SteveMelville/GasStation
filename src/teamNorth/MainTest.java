package teamNorth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void driverRunTest(){
        GasDriver driver = new GasDriver();
        assertEquals(true, driver.run());
    }

    @Test
    void onlyProperTanksCreatable(){
        assertEquals(null, Tank.getTank("87"));
        assertNotNull(Tank.getTank("85"));
        assertNotNull(Tank.getTank("89"));
    }

    @Test
    void correctAmountOfFuelPumped(){
        Pump pump = new Pump(1);
        assertEquals(12.1,pump.pumpFuel(12.1,"85"));
    }

    @Test
    void HandleEmptyPumpWorks(){
        Pump pump = new Pump(1);
        Tank tank = Tank.getTank("85");
    }

}