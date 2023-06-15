import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.Logger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

public class TestPrueba {
    private static final Logger LOGGER = Logger.getLogger(TestPrueba.class.getName());

    @Test //Anotacion
    @DisplayName("Prueba chingona 1")
    void testOneTest(){
        LOGGER.info("Running When Case1: test1_1");
        assertEquals(1.0, 1.0);
    }

    @Test //Anotacion
    @DisplayName("Prueba chingona 2")
    void testTwoTest(){
         LOGGER.info("Running When Case2: test2");
        assertEquals(1.0, 1.0);
    }


    @ParameterizedTest(name = "{0} + {1} + {2}")
	@CsvSource({"9,  0, 1",
			    "1,  2, 7",
			    "3,  2, 5",
			    "0,  1, 9"
	})
    void testThreeTest(int first, int second, int third){
         LOGGER.info("Running When Case3: test3");
         int suma = 0;
         suma=first+second+third;

        assertEquals(10, suma);
    }

    @ParameterizedTest(name = "{0}")
	@CsvFileSource(resources = "testCases.csv")

    void testFourTest(int first){
         LOGGER.info("Running When Case4: test4");


        assertEquals(10, first);
    }

}
