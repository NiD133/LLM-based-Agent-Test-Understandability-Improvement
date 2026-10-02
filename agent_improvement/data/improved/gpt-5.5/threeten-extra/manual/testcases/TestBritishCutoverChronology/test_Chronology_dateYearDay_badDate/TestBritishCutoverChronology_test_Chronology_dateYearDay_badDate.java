package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_Chronology_dateYearDay_badDate {

    @Test
    public void test_Chronology_dateYearDay_badDate() {
        assertThrows(
                DateTimeException.class,
                () -> BritishCutoverChronology.INSTANCE.dateYearDay(2001, 366));
    }
}
