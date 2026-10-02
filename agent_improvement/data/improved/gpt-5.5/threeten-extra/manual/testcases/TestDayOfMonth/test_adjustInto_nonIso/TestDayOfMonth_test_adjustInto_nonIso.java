package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.chrono.JapaneseDate;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_adjustInto_nonIso {

    private static final DayOfMonth TEST_DAY = DayOfMonth.of(12);

    @Test
    public void test_adjustInto_nonIso() {
        assertThrows(DateTimeException.class, () -> TEST_DAY.adjustInto(JapaneseDate.now()));
    }
}
