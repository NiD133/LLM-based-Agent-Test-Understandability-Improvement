package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.chrono.JapaneseDate;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_from_TemporalAccessor_nonIso {

    @Test
    public void test_from_TemporalAccessor_nonIso() {
        LocalDate isoDate = LocalDate.now();
        JapaneseDate nonIsoDate = JapaneseDate.from(isoDate);

        DayOfYear actualDayOfYear = DayOfYear.from(nonIsoDate);

        assertEquals(isoDate.getDayOfYear(), actualDayOfYear.getValue());
    }
}
