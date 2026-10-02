package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestPaxChronology_test_chronology_dateYearDay_badDate {

    @Test
    public void test_chronology_dateYearDay_badDate() {
        assertThrows(DateTimeException.class, () -> PaxChronology.INSTANCE.dateYearDay(2001, 365));
    }
}
