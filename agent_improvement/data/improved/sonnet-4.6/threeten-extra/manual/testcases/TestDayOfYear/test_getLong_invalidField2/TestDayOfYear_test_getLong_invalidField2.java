package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_getLong_invalidField2 {

    // DayOfYear only supports DAY_OF_YEAR; all other fields must throw UnsupportedTemporalTypeException
    private static final DayOfYear TEST = DayOfYear.of(12);

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
    public void test_getLong_invalidField2() {
        // IsoFields.DAY_OF_QUARTER is not supported by DayOfYear — only DAY_OF_YEAR is
        assertThrows(UnsupportedTemporalTypeException.class, () -> TEST.getLong(IsoFields.DAY_OF_QUARTER));
    }
}
