package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_TemporalAmount_Years {

    @Test
    public void test_plus_TemporalAmount_Years() {
        Years five = Years.of(5);

        // Adding zero years is identity
        assertEquals(Years.of(5), five.plus(Years.of(0)));

        // Adding a positive number of years
        assertEquals(Years.of(7), five.plus(Years.of(2)));

        // Adding a negative number of years
        assertEquals(Years.of(3), five.plus(Years.of(-2)));

        // Boundary: one below Integer.MAX_VALUE plus one equals MAX_VALUE
        Years nearMax = Years.of(Integer.MAX_VALUE - 1);
        assertEquals(Years.of(Integer.MAX_VALUE), nearMax.plus(Years.of(1)));

        // Boundary: one above Integer.MIN_VALUE minus one equals MIN_VALUE
        Years nearMin = Years.of(Integer.MIN_VALUE + 1);
        assertEquals(Years.of(Integer.MIN_VALUE), nearMin.plus(Years.of(-1)));
    }
}
