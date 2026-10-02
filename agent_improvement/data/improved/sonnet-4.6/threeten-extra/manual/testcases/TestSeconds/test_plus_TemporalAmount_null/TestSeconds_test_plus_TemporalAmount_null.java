package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_plus_TemporalAmount_null {

    @Test
    public void test_plus_TemporalAmount_null() {
        // plus(TemporalAmount) must reject a null argument with NullPointerException
        assertThrows(NullPointerException.class, () -> Seconds.of(Integer.MIN_VALUE + 1).plus(null));
    }
}
