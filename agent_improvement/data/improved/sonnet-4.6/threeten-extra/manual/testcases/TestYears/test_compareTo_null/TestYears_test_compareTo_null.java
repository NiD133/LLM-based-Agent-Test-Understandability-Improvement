package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_compareTo_null {

    @Test
    public void test_compareTo_null() {
        Years test5 = Years.of(5);
        assertThrows(NullPointerException.class, () -> test5.compareTo(null));
    }
}
