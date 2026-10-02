package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_minus_TemporalAmount_null {

    @Test
    public void test_minus_TemporalAmount_null() {
        Seconds seconds = Seconds.of(Integer.MIN_VALUE + 1);
        assertThrows(NullPointerException.class, () -> seconds.minus(null));
    }
}
