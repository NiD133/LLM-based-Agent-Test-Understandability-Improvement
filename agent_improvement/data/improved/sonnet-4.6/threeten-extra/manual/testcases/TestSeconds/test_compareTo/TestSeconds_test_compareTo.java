package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestSeconds_test_compareTo {

    @Test
    public void test_compareTo() {
        Seconds fiveSeconds = Seconds.of(5);
        Seconds sixSeconds = Seconds.of(6);

        // equal: an instance compared to itself returns 0
        assertEquals(0, fiveSeconds.compareTo(fiveSeconds));
        // less than: the smaller value compared to the larger returns negative
        assertEquals(-1, fiveSeconds.compareTo(sixSeconds));
        // greater than: the larger value compared to the smaller returns positive
        assertEquals(1, sixSeconds.compareTo(fiveSeconds));
    }
}
