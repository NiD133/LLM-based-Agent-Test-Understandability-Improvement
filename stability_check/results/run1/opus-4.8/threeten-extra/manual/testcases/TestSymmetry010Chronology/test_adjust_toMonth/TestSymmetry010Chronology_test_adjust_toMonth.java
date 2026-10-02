package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_adjust_toMonth {

    /**
     * A Symmetry010 date cannot be adjusted with an ISO {@link Month}, because a
     * Symmetry010 year has months whose lengths differ from the ISO calendar.
     * Passing an ISO Month as a TemporalAdjuster must therefore be rejected.
     */
    @Test
    public void test_adjust_toMonth() {
        Symmetry010Date date = Symmetry010Date.of(2000, 1, 4);

        assertThrows(DateTimeException.class, () -> date.with(Month.APRIL));
    }
}
