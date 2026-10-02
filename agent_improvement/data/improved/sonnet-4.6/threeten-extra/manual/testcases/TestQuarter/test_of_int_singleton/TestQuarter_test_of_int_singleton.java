package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_of_int_singleton {

    @Test
    public void test_of_int_singleton() {
        assertEquals(1, Quarter.of(1).getValue());
        assertEquals(2, Quarter.of(2).getValue());
        assertEquals(3, Quarter.of(3).getValue());
        assertEquals(4, Quarter.of(4).getValue());
    }
}
