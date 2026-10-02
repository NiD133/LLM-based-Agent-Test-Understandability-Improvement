package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link TaiInstant#toString()}.
 */
public class TestTaiInstant_test_toString_standard {

    @Test
    public void test_toString_standard() {
        // A TAI instant of 123 seconds plus 123456789 nanoseconds...
        TaiInstant instant = TaiInstant.ofTaiSeconds(123L, 123456789);

        // ...is rendered as "{seconds}.{9-digit nanos}s(TAI)".
        assertEquals("123.123456789s(TAI)", instant.toString());
    }
}
