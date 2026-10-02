package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_minus_TemporalAmount_PeriodMonths {

    @Test
    public void test_minus_TemporalAmount_PeriodMonths() {
        // Years only supports year-based amounts; subtracting a month-based Period must throw DateTimeException
        assertThrows(DateTimeException.class, () -> Years.of(1).minus(Period.ofMonths(2)));
    }
}
