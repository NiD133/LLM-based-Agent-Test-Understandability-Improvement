package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link TaiInstant#toString()} for an instant with a negative second count.
 */
public class TestTaiInstant_test_toString_negative {

    @Test
    public void test_toString_negative() {
        // 123 seconds before the TAI epoch, plus 123456789 nanoseconds.
        TaiInstant instant = TaiInstant.ofTaiSeconds(-123L, 123456789);

        // Format is "{seconds}.{nanos}s(TAI)" with the nanos always nine digits.
        assertEquals("-123.123456789s(TAI)", instant.toString());
    }
}
