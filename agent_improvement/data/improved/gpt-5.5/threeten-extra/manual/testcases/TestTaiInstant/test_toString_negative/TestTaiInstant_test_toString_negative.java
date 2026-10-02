package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_toString_negative {

    @Test
    public void test_toString_negative() {
        TaiInstant instant = TaiInstant.ofTaiSeconds(-123L, 123456789);

        assertEquals("-123.123456789s(TAI)", instant.toString());
    }
}
