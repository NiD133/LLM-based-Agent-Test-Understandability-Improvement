package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_toString_zeroDecimal {

    @Test
    public void test_toString_zeroDecimal() {
        TaiInstant instant = TaiInstant.ofTaiSeconds(0L, 567);

        assertEquals("0.000000567s(TAI)", instant.toString());
    }
}
