package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#plus(java.time.temporal.TemporalAmount)}.
 * <p>
 * Adding another {@code Days} amount should return a {@code Days} whose value
 * is the sum of the two amounts, including the boundary cases where the result
 * reaches {@code Integer.MAX_VALUE} or {@code Integer.MIN_VALUE}.
 */
public class TestDays_test_plus_TemporalAmount_Days {

    @Test
    public void plus_addsTemporalAmountOfDays() {
        Days fiveDays = Days.of(5);

        // adding zero leaves the amount unchanged
        assertEquals(Days.of(5), fiveDays.plus(Days.of(0)));
        // adding a positive amount increases the total
        assertEquals(Days.of(7), fiveDays.plus(Days.of(2)));
        // adding a negative amount decreases the total
        assertEquals(Days.of(3), fiveDays.plus(Days.of(-2)));

        // adding right up to the int boundaries succeeds without overflow
        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE - 1).plus(Days.of(1)));
        assertEquals(Days.of(Integer.MIN_VALUE), Days.of(Integer.MIN_VALUE + 1).plus(Days.of(-1)));
    }
}
