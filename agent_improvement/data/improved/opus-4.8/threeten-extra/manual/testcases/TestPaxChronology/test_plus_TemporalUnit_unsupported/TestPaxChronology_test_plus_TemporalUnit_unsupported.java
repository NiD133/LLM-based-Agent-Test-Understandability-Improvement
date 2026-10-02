package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_plus_TemporalUnit_unsupported {

    /**
     * Adding an amount expressed in a time-based unit (such as MINUTES) to a
     * date-only {@link PaxDate} is not supported and must fail fast.
     */
    @Test
    public void plus_withTimeBasedUnit_throwsUnsupportedTemporalType() {
        PaxDate date = PaxDate.of(2012, 6, 10);

        assertThrows(UnsupportedTemporalTypeException.class, () -> date.plus(0, MINUTES));
    }
}
