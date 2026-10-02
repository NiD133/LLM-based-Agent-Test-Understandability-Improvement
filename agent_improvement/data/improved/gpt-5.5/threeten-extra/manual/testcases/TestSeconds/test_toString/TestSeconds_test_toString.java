package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_toString {

    @Test
    public void test_toString() {
        Seconds fiveSeconds = Seconds.of(5);
        assertEquals("PT5S", fiveSeconds.toString());

        Seconds negativeOneSecond = Seconds.of(-1);
        assertEquals("PT-1S", negativeOneSecond.toString());
    }
}
