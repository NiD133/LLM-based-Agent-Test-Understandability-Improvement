package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link TaiInstant#toString()} when the nanosecond fraction renders
 * with leading zeros and no trailing non-zero digits beyond the supplied value.
 */
public class TestTaiInstant_test_toString_zeroDecimal {

    @Test
    public void toString_padsNanosToNineDigits() {
        // 567 nanoseconds within second 0 must be zero-padded to nine digits.
        TaiInstant instant = TaiInstant.ofTaiSeconds(0L, 567);

        assertEquals("0.000000567s(TAI)", instant.toString());
    }
}
