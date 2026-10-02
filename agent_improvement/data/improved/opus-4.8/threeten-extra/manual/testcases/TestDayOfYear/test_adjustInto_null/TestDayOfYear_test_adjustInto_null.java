package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear}, focusing on {@code adjustInto} with a null target.
 */
public class TestDayOfYear_test_adjustInto_null {

    /** A fixed day-of-year used as the subject under test. */
    private static final DayOfYear DAY_12 = DayOfYear.of(12);

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_adjustInto_null() {
        // Adjusting a null temporal must be rejected with a NullPointerException.
        Temporal nullTarget = null;
        assertThrows(NullPointerException.class, () -> DAY_12.adjustInto(nullTarget));
    }
}
