package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_minus_Period_ISO {

    /**
     * Subtracting an ISO {@link Period} from an {@link InternationalFixedDate} must fail,
     * because the two calendar systems are incompatible. Here a 2-month ISO period is
     * subtracted, which is expected to raise a {@link DateTimeException}.
     */
    @Test
    public void test_minus_Period_ISO() {
        InternationalFixedDate date = InternationalFixedDate.of(2014, 5, 26);

        assertThrows(DateTimeException.class, () -> date.minus(Period.ofMonths(2)));
    }
}
