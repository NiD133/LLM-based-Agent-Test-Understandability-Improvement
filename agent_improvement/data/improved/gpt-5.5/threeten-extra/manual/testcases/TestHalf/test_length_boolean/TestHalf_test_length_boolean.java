package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHalf_test_length_boolean {

    @Test
    public void test_length_boolean() {
        assertEquals(182, Half.H1.length(true));
        assertEquals(181, Half.H1.length(false));

        assertEquals(184, Half.H2.length(true));
        assertEquals(184, Half.H2.length(false));
    }
}
