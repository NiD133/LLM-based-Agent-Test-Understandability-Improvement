package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that subtracting an ISO {@link Period} from a {@link BritishCutoverDate} is rejected.
 *
 * <p>The BritishCutover calendar uses its own chronology; mixing it with an ISO-based
 * {@code Period} (which carries no chronology) is not supported and must throw a
 * {@link DateTimeException}.
 */
public class TestBritishCutoverChronology_test_minus_Period_ISO {

    /**
     * Verifies that calling {@code BritishCutoverDate.minus(Period)} throws
     * {@link DateTimeException} because an ISO {@code Period} cannot be applied to a
     * date in the BritishCutover chronology.
     */
    @Test
    public void test_minus_Period_ISO() {
        BritishCutoverDate date = BritishCutoverDate.of(2014, 5, 26);
        Period isoPeriod = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> date.minus(isoPeriod));
    }
}
