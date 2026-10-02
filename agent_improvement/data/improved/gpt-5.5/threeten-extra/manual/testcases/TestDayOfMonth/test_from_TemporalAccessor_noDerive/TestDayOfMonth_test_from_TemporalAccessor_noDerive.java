package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_from_TemporalAccessor_noDerive {

    @Test
    public void test_from_TemporalAccessor_noDerive() {
        TemporalAccessor timeWithoutDate = LocalTime.NOON;

        assertThrows(DateTimeException.class, () -> DayOfMonth.from(timeWithoutDate));
    }
}
