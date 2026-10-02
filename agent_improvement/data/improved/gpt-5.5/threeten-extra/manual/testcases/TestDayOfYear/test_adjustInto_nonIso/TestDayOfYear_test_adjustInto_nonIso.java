package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.JapaneseDate;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_adjustInto_nonIso {

    private static final DayOfYear TWELFTH_DAY_OF_YEAR = DayOfYear.of(12);

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    @Test
    public void test_adjustInto_nonIso() {
        assertThrows(DateTimeException.class, () -> TWELFTH_DAY_OF_YEAR.adjustInto(JapaneseDate.now()));
    }
}
