package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests adding an ISO {@link Period} to a {@link BritishCutoverDate}.
 */
public class TestBritishCutoverChronology_test_plus_Period_ISO {

    /**
     * Adding an ISO {@code Period} (a different calendar system) to a
     * {@code BritishCutoverDate} is not supported and must be rejected.
     */
    @Test
    public void test_plus_Period_ISO() {
        BritishCutoverDate date = BritishCutoverDate.of(2014, 5, 26);
        Period isoPeriod = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> date.plus(isoPeriod));
    }
}
