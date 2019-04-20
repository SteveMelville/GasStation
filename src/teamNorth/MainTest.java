package teamNorth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {



    @Test
    void onlyProperTanksCreatable(){
        assertEquals(null, Tank.getTank("87"));
        assertNotNull(Tank.getTank("85"));
        assertNotNull(Tank.getTank("89"));
    }

    @Test
    void correctAmountOfFuelPumped(){
        //Pump pump = new Pump(1);
        //assertEquals(12.1,pump.pumpFuel(12.1,"85"));
    }

    @Test
    void pumpFuelTank87(){
        //Pump pump = new Pump(1);
        //assertEquals(12, pump.pumpFuel(12, "87"));
    }

    @Test
    void HandleEmptyPumpWorks(){
        //Pump pump = new Pump(1);
        //assertEquals(-1, pump.pumpFuel(1000000,"85"));
    }

    @Test
    void pumpFuelEmptyTank87(){
        //Pump pump = new Pump(1);
        //assertEquals(-1, pump.pumpFuel(1000000, "87"));
    }
    
    @Test
    void TestTankReorder(){
        Station station = new Station();
        Tank tank = Tank.getTank("85");
        tank.refuelTank(tank.getMaxFuel()-tank.fuelAmount);
        tank.fuelRequest(tank.getMaxFuel()/2);
        assertEquals(tank.getFuelAmount(), tank.getMaxFuel()/2);

        //amount should update
        assertEquals(tank.getFuelAmount(), tank.getMaxFuel());
    }
                            
    @Test
    void TestTankReorderInvalidTank(){
        Station station = new Station();
        Tank tank = Tank.getTank("85");
        tank.refuelTank(tank.getMaxFuel()-tank.fuelAmount);
        tank.fuelRequest(tank.getMaxFuel()/2);
        assertEquals(tank.getFuelAmount(), tank.getMaxFuel()/2);

        //amount should be the same
        assertEquals(tank.getFuelAmount(), tank.getMaxFuel()/2);
    }

    @Test
    void TankRefill(){
        Tank tank = Tank.getTank("85");
        tank.refuelTank(tank.getMaxFuel()-tank.fuelAmount);
        tank.fuelRequest(tank.getMaxFuel());

        tank.refuelTank(20);
        assertEquals(20,tank.getFuelAmount());

    }

    @Test
    void TankFuelRequest(){
        Tank tank = Tank.getTank("85");
        tank.refuelTank(tank.getMaxFuel()-tank.fuelAmount);
        assertEquals(tank.getMaxFuel(), tank.fuelRequest(tank.getMaxFuel()));

        tank.refuelTank(20);

        assertEquals(20, tank.fuelRequest(30));
    }

    @Test
    void maxFuelWorks(){
        Tank tank = Tank.getTank("85");
        tank.fuelRequest(tank.fuelAmount);
        double amountOrdered = tank.maxFuel + 200;

        tank.refuelTank(amountOrdered);
        assertEquals(tank.getMaxFuel(), tank.getFuelAmount());
    }

    @Test
    void fuelExcessWorks(){
        Station station = new Station();
        Tank tank = Tank.getTank("85");
        tank.fuelRequest(tank.getMaxFuel());
        double amountOrdered = tank.maxFuel + 200;

        tank.refuelTank(amountOrdered);
    }

    @Test
    void CarMaxTankSize(){
        Car car = new PickupTruck();


        assertEquals(30.0, car.getMaxTankSize());

        Car car2 = new SemiTruck();

        assertEquals(63.0, car2.getMaxTankSize());
        assertNotEquals(30.0, car.getMaxTankSize());
    }

}
