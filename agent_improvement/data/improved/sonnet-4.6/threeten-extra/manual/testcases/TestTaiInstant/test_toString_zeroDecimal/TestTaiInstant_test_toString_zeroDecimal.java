package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_toString_zeroDecimal {

    /**
     * Verifies that nanoseconds with leading zeros are zero-padded to exactly
     * 9 digits in the TAI string representation, e.g. 567 ns → "0.000000567s(TAI)".
     */
    @Test
    public void test_toString_zeroDecimal() {
        TaiInstant instant = TaiInstant.ofTaiSeconds(0L, 567);
        assertEquals("0.000000567s(TAI)", instant.toString());
    }
}
