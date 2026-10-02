package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_chronology_dateYearDay_badDate {

    @Test
    public void test_chronology_dateYearDay_badDate() {
        int nonLeapYear = 2001;
        int leapYearOnlyDay = 366;

        assertThrows(DateTimeException.class,
                () -> InternationalFixedChronology.INSTANCE.dateYearDay(nonLeapYear, leapYearOnlyDay));
    }
}
