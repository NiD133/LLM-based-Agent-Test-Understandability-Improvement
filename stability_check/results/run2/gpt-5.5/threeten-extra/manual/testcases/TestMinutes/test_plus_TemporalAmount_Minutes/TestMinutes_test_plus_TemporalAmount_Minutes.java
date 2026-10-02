package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_plus_TemporalAmount_Minutes {

    @Test
    public void test_plus_TemporalAmount_Minutes() {
        Minutes fiveMinutes = Minutes.of(5);

        assertEquals(Minutes.of(5), fiveMinutes.plus(Minutes.of(0)));
        assertEquals(Minutes.of(7), fiveMinutes.plus(Minutes.of(2)));
        assertEquals(Minutes.of(3), fiveMinutes.plus(Minutes.of(-2)));

        assertEquals(
                Minutes.of(Integer.MAX_VALUE),
                Minutes.of(Integer.MAX_VALUE - 1).plus(Minutes.of(1)));
        assertEquals(
                Minutes.of(Integer.MIN_VALUE),
                Minutes.of(Integer.MIN_VALUE + 1).plus(Minutes.of(-1)));
    }
}
