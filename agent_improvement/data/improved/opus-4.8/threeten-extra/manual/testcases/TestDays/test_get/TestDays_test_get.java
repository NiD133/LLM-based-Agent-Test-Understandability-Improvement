package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#get(java.time.temporal.TemporalUnit)}.
 */
public class TestDays_test_get {

    @Test
    public void test_get_returnsDayCount_forDaysUnit() {
        Days sixDays = Days.of(6);

        long amountInDays = sixDays.get(ChronoUnit.DAYS);

        assertEquals(6, amountInDays);
    }
}
