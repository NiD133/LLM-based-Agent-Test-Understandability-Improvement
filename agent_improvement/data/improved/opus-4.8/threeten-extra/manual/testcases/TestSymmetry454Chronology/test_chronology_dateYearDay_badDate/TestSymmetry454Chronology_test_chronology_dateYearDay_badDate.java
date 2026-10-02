package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_chronology_dateYearDay_badDate {

    /**
     * The Symmetry454 year 2000 has 364 days, so day-of-year 365 is out of range
     * and {@code dateYearDay} must reject it with a {@link DateTimeException}.
     */
    @Test
    public void test_chronology_dateYearDay_badDate() {
        int yearWith364Days = 2000;
        int dayOfYearPastEndOfYear = 365;

        assertThrows(DateTimeException.class,
                () -> Symmetry454Chronology.INSTANCE.dateYearDay(yearWith364Days, dayOfYearPastEndOfYear));
    }
}
