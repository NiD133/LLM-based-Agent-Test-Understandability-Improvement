package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_multipliedBy_negate {

    @Test
    public void test_multipliedBy_negate() {
        Seconds fiveSeconds = Seconds.of(5);

        assertEquals(Seconds.of(-15), fiveSeconds.multipliedBy(-3));
    }
}
