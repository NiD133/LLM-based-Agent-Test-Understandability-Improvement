package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_adjust_toMonth {

    /**
     * The International Fixed calendar has no concept of ISO months, so adjusting
     * an IFC date to a {@link Month} enum value must throw {@link DateTimeException}.
     */
    @Test
    public void test_adjust_toMonth() {
        InternationalFixedDate date = InternationalFixedDate.of(2000, 1, 4);
        // Month.APRIL is an ISO TemporalAdjuster that is incompatible with IFC dates
        assertThrows(DateTimeException.class, () -> date.with(Month.APRIL));
    }
}
