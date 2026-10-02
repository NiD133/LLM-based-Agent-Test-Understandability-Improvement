package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_of_int_singleton_equals {

    @Test
    public void test_of_int_singleton_equals() {
        AmPm am = AmPm.of(0);
        assertEquals(0, am.getValue());

        AmPm pm = AmPm.of(1);
        assertEquals(1, pm.getValue());
    }
}
