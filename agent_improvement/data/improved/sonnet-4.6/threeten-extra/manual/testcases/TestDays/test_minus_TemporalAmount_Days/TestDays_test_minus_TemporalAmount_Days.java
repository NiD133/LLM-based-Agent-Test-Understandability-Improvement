package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Days.minus(TemporalAmount) with Days argument")
public class TestDays_test_minus_TemporalAmount_Days {

    @Test
    @DisplayName("subtracting Days amounts produces correct results, including boundary values")
    public void test_minus_TemporalAmount_Days() {
        Days five = Days.of(5);

        // Subtracting zero leaves the value unchanged
        assertEquals(Days.of(5), five.minus(Days.of(0)));

        // Subtracting a positive amount decreases the value
        assertEquals(Days.of(3), five.minus(Days.of(2)));

        // Subtracting a negative amount increases the value
        assertEquals(Days.of(7), five.minus(Days.of(-2)));

        // Subtracting -1 from (MAX_VALUE - 1) reaches Integer.MAX_VALUE without overflow
        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE - 1).minus(Days.of(-1)));

        // Subtracting 1 from (MIN_VALUE + 1) reaches Integer.MIN_VALUE without overflow
        assertEquals(Days.of(Integer.MIN_VALUE), Days.of(Integer.MIN_VALUE + 1).minus(Days.of(1)));
    }
}
