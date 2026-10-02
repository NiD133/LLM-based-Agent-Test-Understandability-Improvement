package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_TemporalAmount_PeriodMonths {

    @Test
    public void test_plus_TemporalAmount_PeriodMonths() {
        // Years.plus() only accepts amounts convertible to whole years;
        // a Period of months cannot be converted, so DateTimeException is expected.
        assertThrows(DateTimeException.class, () -> Years.of(1).plus(Period.ofMonths(2)));
    }
}
