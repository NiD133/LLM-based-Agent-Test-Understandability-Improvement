package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that subtracting an ISO {@link Period} from a {@link PaxDate} is rejected.
 *
 * <p>A {@code PaxDate} belongs to the Pax calendar system, whereas {@code Period}
 * is expressed in ISO years/months/days. Mixing the two chronologies is not
 * allowed, so the subtraction must fail with a {@link DateTimeException}.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_minus_Period_ISO {

    @Test
    public void minus_isoPeriod_throwsBecauseChronologiesDiffer() {
        PaxDate paxDate = PaxDate.of(2014, 5, 26);
        Period isoPeriod = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> paxDate.minus(isoPeriod));
    }
}
