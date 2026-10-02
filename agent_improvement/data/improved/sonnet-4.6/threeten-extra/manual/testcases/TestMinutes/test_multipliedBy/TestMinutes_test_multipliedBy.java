package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_multipliedBy {

    @Test
    public void test_multipliedBy() {
        Minutes fiveMinutes = Minutes.of(5);
        assertEquals(Minutes.of(0),   fiveMinutes.multipliedBy(0));
        assertEquals(Minutes.of(5),   fiveMinutes.multipliedBy(1));
        assertEquals(Minutes.of(10),  fiveMinutes.multipliedBy(2));
        assertEquals(Minutes.of(15),  fiveMinutes.multipliedBy(3));
        assertEquals(Minutes.of(-15), fiveMinutes.multipliedBy(-3));
    }
}
