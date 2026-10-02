package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#from(java.time.temporal.TemporalAmount)} when given a
 * zero-day {@link Period}.
 */
public class TestDays_test_from_Period_P0D {

    /**
     * Converting a period of zero days ("P0D") yields {@code Days.of(0)}.
     */
    @Test
    public void test_from_Period_P0D() {
        Days converted = Days.from(Period.ofDays(0));

        assertEquals(Days.of(0), converted);
    }
}
