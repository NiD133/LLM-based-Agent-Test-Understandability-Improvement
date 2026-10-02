package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_plus_TemporalUnit_unsupported {

    /**
     * Adding an amount expressed in a time-based unit (e.g. MINUTES) to a
     * date-only {@link InternationalFixedDate} is not supported and must fail.
     */
    @Test
    public void plus_withTimeBasedUnit_throwsUnsupportedTemporalType() {
        InternationalFixedDate date = InternationalFixedDate.of(2012, 6, 28);

        assertThrows(UnsupportedTemporalTypeException.class, () -> date.plus(0, MINUTES));
    }
}
