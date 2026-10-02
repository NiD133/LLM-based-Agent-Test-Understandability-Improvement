package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_multipliedBy_negate {

    @Test
    public void test_multipliedBy_negate() {
        // Multiplying a positive amount by a negative scalar should negate the result:
        // 5 seconds * -3 = -15 seconds
        Seconds fiveSeconds = Seconds.of(5);
        Seconds result = fiveSeconds.multipliedBy(-3);
        assertEquals(Seconds.of(-15), result);
    }
}
