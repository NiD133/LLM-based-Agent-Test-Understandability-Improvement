package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_compareTo_null {

    @Test
    public void test_compareTo_null() {
        Weeks test5 = Weeks.of(5);
        assertThrows(NullPointerException.class, () -> test5.compareTo(null));
    }
}
