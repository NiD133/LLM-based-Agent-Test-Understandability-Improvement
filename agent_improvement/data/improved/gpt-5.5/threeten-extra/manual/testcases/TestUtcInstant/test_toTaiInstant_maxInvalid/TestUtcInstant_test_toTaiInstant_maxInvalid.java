package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_toTaiInstant_maxInvalid {

    @Test
    public void test_toTaiInstant_maxInvalid() {
        UtcInstant maxSupportedDay = UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, 0);

        assertThrows(ArithmeticException.class, () -> maxSupportedDay.toTaiInstant());
    }
}
