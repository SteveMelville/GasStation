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
    void pumpFuelTank87(){
        Pump pump = new Pump(1);
        assertEquals(12, pump.pumpFuel(12, "87"));
    }

    @Test
    void HandleEmptyPumpWorks(){
        Pump pump = new Pump(1);
        assertEquals(-1, pump.pumpFuel(1000000,"85"));
    }

    @Test
    void pumpFuelEmptyTank87(){
        Pump pump = new Pump(1);
        assertEquals(-1, pump.pumpFuel(1000000, "87"));
    }
    
    @Test
    void TestTankReorder(){
        Station station = new Station();
        Tank tank = Tank.getTank("85");
        tank.fuelRequest(5000);
        assertEquals(tank.getFuelAmount(), 5000);

        //amount should update
        station.TankReorder("85");
        assertEquals(tank.getFuelAmount(), 10000);                    
    }
                            
    @Test
    void TestTankReorderInvalidTank(){
        Station station = new Station();
        Tank tank = Tank.getTank("85");
        tank.fuelRequest(5000);
        assertEquals(tank.getFuelAmount(), 5000);

        //amount should be the same
        station.TankReorder("87");
        assertEquals(tank.getFuelAmount(), 5000);                    
    }

}
