package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestYears_test_compareTo {

    @Test
    public void test_compareTo() {
        Years fewer = Years.of(5);
        Years more  = Years.of(6);

        // equal: comparing an instance to itself must return 0
        assertEquals(0, fewer.compareTo(fewer));

        // less-than: smaller years compared to larger years returns negative
        assertEquals(-1, fewer.compareTo(more));

        // greater-than: larger years compared to smaller years returns positive
        assertEquals(1, more.compareTo(fewer));
    }
}
