package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_plus_TemporalUnit_unsupported {

    /**
     * Adding an amount measured in a time-based unit (e.g. MINUTES) to a
     * date-only {@link Symmetry010Date} is not supported and must fail.
     */
    @Test
    public void plus_withTimeBasedUnit_throwsUnsupportedTemporalType() {
        Symmetry010Date date = Symmetry010Date.of(2012, 6, 28);

        assertThrows(UnsupportedTemporalTypeException.class, () -> date.plus(0, MINUTES));
    }
}
