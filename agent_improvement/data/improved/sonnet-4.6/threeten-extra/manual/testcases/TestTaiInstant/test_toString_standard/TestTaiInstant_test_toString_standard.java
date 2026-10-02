package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_toString_standard {

    // TaiInstant.toString() format: "{seconds}.{9-digit nanosOfSecond}s(TAI)"
    @Test
    public void test_toString_standard() {
        TaiInstant t = TaiInstant.ofTaiSeconds(123L, 123456789);
        assertEquals("123.123456789s(TAI)", t.toString());
    }
}
