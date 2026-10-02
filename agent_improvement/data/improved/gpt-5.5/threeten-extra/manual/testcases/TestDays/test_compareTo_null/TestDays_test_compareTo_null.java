package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_compareTo_null {

    @Test
    public void test_compareTo_null() {
        Days fiveDays = Days.of(5);

        assertThrows(NullPointerException.class, () -> fiveDays.compareTo(null));
    }
}
