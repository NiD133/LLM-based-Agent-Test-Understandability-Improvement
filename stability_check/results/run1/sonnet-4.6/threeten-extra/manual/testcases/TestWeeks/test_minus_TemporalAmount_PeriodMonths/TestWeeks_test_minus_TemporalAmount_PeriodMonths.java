package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_TemporalAmount_PeriodMonths {

    @Test
    @DisplayName("minus(Period.ofMonths) throws DateTimeException because months cannot be converted to weeks")
    public void test_minus_TemporalAmount_PeriodMonths() {
        assertThrows(DateTimeException.class, () -> Weeks.of(1).minus(Period.ofMonths(2)));
    }
}
