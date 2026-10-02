package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that subtracting an ISO {@link Period} from a {@link JulianDate} is rejected.
 * <p>
 * A {@code JulianDate} is part of the Julian chronology, whereas an ISO {@code Period}
 * belongs to the ISO chronology. Mixing the two is not supported, so the subtraction
 * must fail with a {@link DateTimeException}.
 */
public class TestJulianChronology_test_minus_Period_ISO {

    @Test
    public void minus_isoPeriod_throwsDateTimeException() {
        JulianDate date = JulianDate.of(2014, 5, 26);

        assertThrows(DateTimeException.class, () -> date.minus(Period.ofMonths(2)));
    }
}
