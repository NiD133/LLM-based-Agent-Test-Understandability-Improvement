package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_plus_TemporalUnit_unsupported {

    /**
     * Adding an amount expressed in a time-based unit (such as MINUTES) to a
     * date-only Symmetry454Date is not supported and must be rejected.
     */
    @Test
    public void test_plus_with_unsupported_time_unit_throws() {
        Symmetry454Date date = Symmetry454Date.of(2012, 6, 28);

        assertThrows(UnsupportedTemporalTypeException.class, () -> date.plus(0, MINUTES));
    }
}
