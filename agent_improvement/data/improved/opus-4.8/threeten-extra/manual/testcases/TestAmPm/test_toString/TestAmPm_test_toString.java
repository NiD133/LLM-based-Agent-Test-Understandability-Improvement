package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AmPm#toString()} returns the enum constant's name.
 */
public class TestAmPm_test_toString {

    @Test
    public void test_toString() {
        assertEquals("AM", AmPm.AM.toString());
        assertEquals("PM", AmPm.PM.toString());
    }
}
