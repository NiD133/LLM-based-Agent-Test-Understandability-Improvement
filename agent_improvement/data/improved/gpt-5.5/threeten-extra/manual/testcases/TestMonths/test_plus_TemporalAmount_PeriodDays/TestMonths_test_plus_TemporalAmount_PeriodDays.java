package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestMonths_test_plus_TemporalAmount_PeriodDays {

    @Test
    public void test_plus_TemporalAmount_PeriodDays() {
        Months oneMonth = Months.of(1);
        Period twoDays = Period.ofDays(2);

        assertThrows(DateTimeException.class, () -> oneMonth.plus(twoDays));
    }
}
