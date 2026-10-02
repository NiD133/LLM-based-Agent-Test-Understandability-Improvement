package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_compareTo {

    @Test
    public void test_compareTo() {
        Seconds fiveSeconds = Seconds.of(5);
        Seconds sixSeconds = Seconds.of(6);

        assertEquals(0, fiveSeconds.compareTo(fiveSeconds));
        assertEquals(-1, fiveSeconds.compareTo(sixSeconds));
        assertEquals(1, sixSeconds.compareTo(fiveSeconds));
    }
}
