package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_TemporalAmount_null {

    @Test
    public void test_minus_TemporalAmount_null() {
        assertThrows(NullPointerException.class, () -> Weeks.of(Integer.MIN_VALUE + 1).minus(null));
    }
}
