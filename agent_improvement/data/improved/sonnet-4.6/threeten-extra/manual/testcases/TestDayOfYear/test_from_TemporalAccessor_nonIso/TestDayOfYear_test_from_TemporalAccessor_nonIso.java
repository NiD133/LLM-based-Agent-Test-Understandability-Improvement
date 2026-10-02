package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.JapaneseDate;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_from_TemporalAccessor_nonIso {

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_from_TemporalAccessor_nonIso() {
        // Verify that DayOfYear.from() correctly converts a non-ISO (Japanese) date
        // by falling back to LocalDate conversion and extracting DAY_OF_YEAR.
        LocalDate isoDate = LocalDate.now();
        JapaneseDate japaneseDate = JapaneseDate.from(isoDate);
        int expectedDayOfYear = isoDate.getDayOfYear();

        DayOfYear result = DayOfYear.from(japaneseDate);

        assertEquals(expectedDayOfYear, result.getValue());
    }
}
