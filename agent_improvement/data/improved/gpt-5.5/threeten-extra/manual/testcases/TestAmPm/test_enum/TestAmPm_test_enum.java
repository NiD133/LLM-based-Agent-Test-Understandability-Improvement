package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_enum {

    @Test
    public void test_enum() {
        AmPm valueByName = AmPm.valueOf("AM");
        AmPm firstDeclaredValue = AmPm.values()[0];

        assertEquals(AmPm.AM, valueByName);
        assertEquals(AmPm.AM, firstDeclaredValue);
    }
}
