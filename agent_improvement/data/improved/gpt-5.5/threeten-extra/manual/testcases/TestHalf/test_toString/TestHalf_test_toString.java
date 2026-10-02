package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHalf_test_toString {

    @Test
    public void test_toString() {
        assertEquals("H1", Half.H1.toString());
        assertEquals("H2", Half.H2.toString());
    }
}
