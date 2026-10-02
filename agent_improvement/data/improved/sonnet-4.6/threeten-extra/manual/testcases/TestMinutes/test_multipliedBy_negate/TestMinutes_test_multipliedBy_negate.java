package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_multipliedBy_negate {

    @Test
    public void test_multipliedBy_negate() {
        // Multiplying by a negative scalar should negate and scale the result:
        // 5 * -3 = -15
        Minutes fiveMinutes = Minutes.of(5);
        assertEquals(Minutes.of(-15), fiveMinutes.multipliedBy(-3));
    }
}
