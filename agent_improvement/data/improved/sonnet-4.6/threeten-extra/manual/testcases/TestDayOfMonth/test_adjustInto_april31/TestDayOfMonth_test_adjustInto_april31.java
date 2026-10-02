package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Verifies that adjusting a temporal to day 31 fails when the target month has fewer than 31 days.
 */
public class TestDayOfMonth_test_adjustInto_april31 {

    @Test
    public void test_adjustInto_april31() {
        // April has only 30 days, so adjusting to day 31 must throw DateTimeException
        LocalDate base = LocalDate.of(2007, 4, 1);
        DayOfMonth test = DayOfMonth.of(31);
        assertThrows(DateTimeException.class, () -> test.adjustInto(base));
    }
}
