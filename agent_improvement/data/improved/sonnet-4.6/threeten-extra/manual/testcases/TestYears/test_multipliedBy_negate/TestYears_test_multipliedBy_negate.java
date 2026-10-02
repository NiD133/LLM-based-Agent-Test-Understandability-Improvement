package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_multipliedBy_negate {

    @Test
    public void test_multipliedBy_negate() {
        Years test5 = Years.of(5);
        assertEquals(Years.of(-15), test5.multipliedBy(-3));
    }
}
