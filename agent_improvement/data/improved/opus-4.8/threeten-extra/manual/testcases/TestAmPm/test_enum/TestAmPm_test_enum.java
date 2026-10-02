package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the basic enum behaviour of {@link AmPm}.
 */
public class TestAmPm_test_enum {

    @Test
    public void test_enum() {
        // valueOf resolves the "AM" name to the AM constant.
        assertEquals(AmPm.AM, AmPm.valueOf("AM"));
        // AM is declared first, so it is the first entry in values().
        assertEquals(AmPm.AM, AmPm.values()[0]);
    }
}
