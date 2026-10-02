package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestDays_test_plus_TemporalAmount_PeriodYears {

    @Test
    public void test_plus_TemporalAmount_PeriodYears() {
        assertThrows(DateTimeException.class, () -> Days.of(1).plus(Period.ofYears(2)));
    }
}
