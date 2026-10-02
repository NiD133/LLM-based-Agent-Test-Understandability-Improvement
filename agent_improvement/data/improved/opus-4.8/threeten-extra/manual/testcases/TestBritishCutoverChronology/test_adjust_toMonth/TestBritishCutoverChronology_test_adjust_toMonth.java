package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests how {@link BritishCutoverDate} reacts when adjusted with an ISO
 * {@link Month}.
 */
public class TestBritishCutoverChronology_test_adjust_toMonth {

    /**
     * A {@code java.time.Month} is an ISO-only temporal adjuster, so it cannot
     * be used to adjust a British cutover date. Attempting the adjustment must
     * fail with a {@link DateTimeException}.
     */
    @Test
    public void test_adjust_toMonth() {
        BritishCutoverDate date = BritishCutoverDate.of(2000, 1, 4);

        assertThrows(DateTimeException.class, () -> date.with(Month.APRIL));
    }
}
