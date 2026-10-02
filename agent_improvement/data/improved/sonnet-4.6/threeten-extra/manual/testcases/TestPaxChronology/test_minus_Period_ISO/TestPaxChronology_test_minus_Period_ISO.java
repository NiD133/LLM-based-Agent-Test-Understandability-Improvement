package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that subtracting an ISO {@link Period} from a {@link PaxDate} is rejected.
 *
 * <p>ISO periods belong to a different calendar system (Gregorian/ISO), so mixing
 * them with a Pax date is not allowed and must throw {@link DateTimeException}.
 */
@SuppressWarnings("static-method")
public class TestPaxChronology_test_minus_Period_ISO {

    @Test
    public void test_minus_Period_ISO() {
        // PaxDate rejects ISO periods because they originate from a different chronology.
        assertThrows(DateTimeException.class,
                () -> PaxDate.of(2014, 5, 26).minus(Period.ofMonths(2)));
    }
}
