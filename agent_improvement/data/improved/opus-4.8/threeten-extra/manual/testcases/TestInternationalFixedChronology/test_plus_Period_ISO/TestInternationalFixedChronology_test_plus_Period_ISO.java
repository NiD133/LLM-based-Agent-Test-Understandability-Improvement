package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link InternationalFixedDate#plus} rejects an ISO {@link Period}.
 *
 * <p>An ISO {@code Period} is expressed in ISO years/months/days, which do not map
 * onto the International Fixed calendar's 13-month structure. Adding one must therefore
 * fail rather than silently produce a wrong date.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_plus_Period_ISO {

    @Test
    public void plus_isoPeriod_throwsDateTimeException() {
        InternationalFixedDate date = InternationalFixedDate.of(2014, 5, 26);
        Period isoPeriod = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> date.plus(isoPeriod));
    }
}
