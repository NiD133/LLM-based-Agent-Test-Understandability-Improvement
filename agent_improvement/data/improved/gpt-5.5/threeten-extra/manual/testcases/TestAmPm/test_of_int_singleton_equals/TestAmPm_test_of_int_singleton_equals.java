package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_of_int_singleton_equals {

    @Test
    public void test_of_int_singleton_equals() {
        assertAmPmValueRoundTrips(0);
        assertAmPmValueRoundTrips(1);
    }

    private void assertAmPmValueRoundTrips(int amPmValue) {
        AmPm test = AmPm.of(amPmValue);
        assertEquals(amPmValue, test.getValue());
    }
}
