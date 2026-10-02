package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestMinutes_test_minus_TemporalAmount_Minutes {

    @Test
    public void test_minus_TemporalAmount_Minutes() {
        Minutes fiveMinutes = Minutes.of(5);

        // Subtracting zero leaves the value unchanged
        assertEquals(Minutes.of(5), fiveMinutes.minus(Minutes.of(0)));

        // Subtracting a positive amount decreases the value
        assertEquals(Minutes.of(3), fiveMinutes.minus(Minutes.of(2)));

        // Subtracting a negative amount increases the value
        assertEquals(Minutes.of(7), fiveMinutes.minus(Minutes.of(-2)));

        // Subtracting -1 from MAX_VALUE - 1 reaches Integer.MAX_VALUE without overflow
        assertEquals(Minutes.of(Integer.MAX_VALUE),
                Minutes.of(Integer.MAX_VALUE - 1).minus(Minutes.of(-1)));

        // Subtracting 1 from MIN_VALUE + 1 reaches Integer.MIN_VALUE without overflow
        assertEquals(Minutes.of(Integer.MIN_VALUE),
                Minutes.of(Integer.MIN_VALUE + 1).minus(Minutes.of(1)));
    }
}
