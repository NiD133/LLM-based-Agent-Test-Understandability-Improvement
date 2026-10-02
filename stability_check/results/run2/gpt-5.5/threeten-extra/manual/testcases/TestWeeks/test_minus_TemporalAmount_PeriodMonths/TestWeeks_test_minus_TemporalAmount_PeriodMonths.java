package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_TemporalAmount_PeriodMonths {

    @Test
    public void test_minus_TemporalAmount_PeriodMonths() {
        Period unsupportedMonthAmount = Period.ofMonths(2);

        assertThrows(
                DateTimeException.class,
                () -> Weeks.of(1).minus(unsupportedMonthAmount));
    }
}
