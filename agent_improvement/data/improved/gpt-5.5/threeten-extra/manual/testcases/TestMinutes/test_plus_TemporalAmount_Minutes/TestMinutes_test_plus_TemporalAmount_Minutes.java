package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_plus_TemporalAmount_Minutes {

    @Test
    public void test_plus_TemporalAmount_Minutes() {
        Minutes fiveMinutes = Minutes.of(5);

        assertPlusEquals(Minutes.of(5), fiveMinutes, Minutes.of(0));
        assertPlusEquals(Minutes.of(7), fiveMinutes, Minutes.of(2));
        assertPlusEquals(Minutes.of(3), fiveMinutes, Minutes.of(-2));
        assertPlusEquals(
                Minutes.of(Integer.MAX_VALUE),
                Minutes.of(Integer.MAX_VALUE - 1),
                Minutes.of(1));
        assertPlusEquals(
                Minutes.of(Integer.MIN_VALUE),
                Minutes.of(Integer.MIN_VALUE + 1),
                Minutes.of(-1));
    }

    private static void assertPlusEquals(Minutes expected, Minutes base, Minutes amountToAdd) {
        assertEquals(expected, base.plus(amountToAdd));
    }
}
