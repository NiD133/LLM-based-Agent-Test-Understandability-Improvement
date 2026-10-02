package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that converting a UtcInstant to a TaiInstant overflows when the
 * Modified Julian Day is at its maximum possible value.
 */
public class TestUtcInstant_test_toTaiInstant_maxInvalid {

    @Test
    public void toTaiInstant_atMaxModifiedJulianDay_throwsArithmeticException() {
        UtcInstant utcAtMaxDay = UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, 0);

        assertThrows(ArithmeticException.class, () -> utcAtMaxDay.toTaiInstant());
    }
}
