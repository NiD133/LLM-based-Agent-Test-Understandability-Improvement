package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#from(java.time.temporal.TemporalAmount)} when the source amount
 * mixes weeks and days.
 */
public class TestDays_test_from_P2W3D {

    /**
     * {@code Days.from} must convert a weeks-and-days amount into a single day count,
     * so 2 weeks plus 3 days equals 2 * 7 + 3 = 17 days.
     */
    @Test
    public void from_amountOf2Weeks3Days_returns17Days() {
        int expectedDays = 2 * 7 + 3;
        Days result = Days.from(new MockWeeksDays(2, 3));

        assertEquals(Days.of(expectedDays), result);
    }
}
