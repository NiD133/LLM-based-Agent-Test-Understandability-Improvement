package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_lengthOfMonth_specific {

    @Test
    public void test_lengthOfMonth_specific() {
        assertEquals(28, Symmetry454Date.of(2000, 12, 28).lengthOfMonth());
        assertEquals(35, Symmetry454Date.of(2004, 12, 28).lengthOfMonth());
    }
}
