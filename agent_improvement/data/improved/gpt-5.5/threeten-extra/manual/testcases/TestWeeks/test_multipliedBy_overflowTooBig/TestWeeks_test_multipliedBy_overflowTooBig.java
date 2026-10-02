package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_multipliedBy_overflowTooBig {

    private static final int SMALLEST_WEEKS_VALUE_THAT_OVERFLOWS_WHEN_DOUBLED =
            Integer.MAX_VALUE / 2 + 1;
    private static final int DOUBLE = 2;

    @Test
    public void test_multipliedBy_overflowTooBig() {
        assertThrows(
                ArithmeticException.class,
                () -> Weeks.of(SMALLEST_WEEKS_VALUE_THAT_OVERFLOWS_WHEN_DOUBLED).multipliedBy(DOUBLE));
    }
}
