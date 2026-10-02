package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_of_int_singleton {

    @Test
    public void test_of_int_singleton() {
        for (int quarterValue = 1; quarterValue <= 4; quarterValue++) {
            Quarter quarter = Quarter.of(quarterValue);

            assertEquals(quarterValue, quarter.getValue());
        }
    }
}
