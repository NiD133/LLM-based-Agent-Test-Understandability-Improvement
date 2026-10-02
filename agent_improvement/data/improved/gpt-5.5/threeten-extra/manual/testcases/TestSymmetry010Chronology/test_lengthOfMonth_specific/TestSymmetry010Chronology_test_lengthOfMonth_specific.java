package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestSymmetry010Chronology_test_lengthOfMonth_specific {

    @Test
    public void test_lengthOfMonth_specific() {
        assertEquals(30, Symmetry010Date.of(2000, 12, 1).lengthOfMonth());
        assertEquals(37, Symmetry010Date.of(2004, 12, 1).lengthOfMonth());
    }
}
