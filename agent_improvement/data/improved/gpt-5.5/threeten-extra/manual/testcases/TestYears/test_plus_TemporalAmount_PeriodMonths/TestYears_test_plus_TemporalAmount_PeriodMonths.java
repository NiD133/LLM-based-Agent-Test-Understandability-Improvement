package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_TemporalAmount_PeriodMonths {

    @Test
    public void test_plus_TemporalAmount_PeriodMonths() {
        assertThrows(DateTimeException.class, () -> Years.of(1).plus(Period.ofMonths(2)));
    }
}
