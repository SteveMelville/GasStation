package teamNorth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void driverRunTest(){
        GasDriver driver = new GasDriver();
        assertEquals(true, driver.run());
    }

}