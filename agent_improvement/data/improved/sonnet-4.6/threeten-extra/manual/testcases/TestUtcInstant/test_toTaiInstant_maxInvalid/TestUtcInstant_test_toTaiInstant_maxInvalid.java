package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_toTaiInstant_maxInvalid {

    /**
     * Verifies that converting a UtcInstant with Long.MAX_VALUE as the Modified Julian Day
     * to a TaiInstant throws ArithmeticException due to numeric overflow in the TAI conversion.
     */
    @Test
    public void test_toTaiInstant_maxInvalid() {
        UtcInstant utc = UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, 0);
        assertThrows(ArithmeticException.class, () -> utc.toTaiInstant());
    }
}
