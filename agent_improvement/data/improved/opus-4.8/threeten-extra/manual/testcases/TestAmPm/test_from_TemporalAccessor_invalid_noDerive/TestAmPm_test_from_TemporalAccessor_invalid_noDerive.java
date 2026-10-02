package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link AmPm#from(java.time.temporal.TemporalAccessor)} when the supplied
 * temporal cannot provide an AM/PM value.
 */
public class TestAmPm_test_from_TemporalAccessor_invalid_noDerive {

    /**
     * A {@link LocalDate} holds no time-of-day information, so it cannot supply the
     * AMPM_OF_DAY field. {@code AmPm.from} should therefore reject it with a
     * {@link DateTimeException} rather than deriving a value.
     */
    @Test
    public void test_from_TemporalAccessor_invalid_noDerive() {
        LocalDate dateWithoutTime = LocalDate.of(2007, 7, 30);

        assertThrows(DateTimeException.class, () -> AmPm.from(dateWithoutTime));
    }
}
