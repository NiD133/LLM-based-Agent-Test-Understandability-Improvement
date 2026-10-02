package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link Symmetry010Date} rejects arithmetic with an ISO {@link Period}.
 *
 * <p>{@code Symmetry010Date.plus(TemporalAmount)} requires the amount to be expressed
 * in the Symmetry010 chronology. An ISO {@link Period} belongs to the ISO chronology,
 * so adding one must fail rather than silently mixing calendar systems.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_plus_Period_ISO {

    @Test
    public void plus_isoPeriod_throwsDateTimeException() {
        // An arbitrary valid Symmetry010 date.
        Symmetry010Date date = Symmetry010Date.of(2014, 5, 26);

        // Adding an ISO Period (2 months) is a cross-chronology operation and is not allowed.
        assertThrows(DateTimeException.class, () -> date.plus(Period.ofMonths(2)));
    }
}
