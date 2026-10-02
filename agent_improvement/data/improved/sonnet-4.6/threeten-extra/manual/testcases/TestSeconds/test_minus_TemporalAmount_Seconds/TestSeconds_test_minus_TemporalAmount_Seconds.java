package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestSeconds_test_minus_TemporalAmount_Seconds {

    @Test
    public void test_minus_TemporalAmount_Seconds() {
        Seconds five = Seconds.of(5);

        // Subtracting zero leaves the value unchanged
        assertEquals(Seconds.of(5), five.minus(Seconds.of(0)));

        // Subtracting a positive amount decreases the value
        assertEquals(Seconds.of(3), five.minus(Seconds.of(2)));

        // Subtracting a negative amount increases the value
        assertEquals(Seconds.of(7), five.minus(Seconds.of(-2)));

        // Boundary: (MAX_VALUE - 1) minus (-1) reaches exactly Integer.MAX_VALUE without overflow
        assertEquals(Seconds.of(Integer.MAX_VALUE),
                Seconds.of(Integer.MAX_VALUE - 1).minus(Seconds.of(-1)));

        // Boundary: (MIN_VALUE + 1) minus 1 reaches exactly Integer.MIN_VALUE without overflow
        assertEquals(Seconds.of(Integer.MIN_VALUE),
                Seconds.of(Integer.MIN_VALUE + 1).minus(Seconds.of(1)));
    }
}
