package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_adjust_toMonth {

    /**
     * Adjusting an InternationalFixedDate with an ISO {@link Month} must fail:
     * the International Fixed calendar has 13 months, so an ISO month (1-12) is
     * not a valid adjuster for it and {@code with(...)} should reject it.
     */
    @Test
    public void test_adjust_toMonth() {
        InternationalFixedDate fixed = InternationalFixedDate.of(2000, 1, 4);

        assertThrows(DateTimeException.class, () -> fixed.with(Month.APRIL));
    }
}
