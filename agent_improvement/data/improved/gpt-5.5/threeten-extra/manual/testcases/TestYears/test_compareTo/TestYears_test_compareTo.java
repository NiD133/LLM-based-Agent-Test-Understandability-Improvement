package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_compareTo {

    @Test
    public void test_compareTo() {
        Years fiveYears = Years.of(5);
        Years sixYears = Years.of(6);

        assertEquals(0, fiveYears.compareTo(fiveYears));
        assertEquals(-1, fiveYears.compareTo(sixYears));
        assertEquals(1, sixYears.compareTo(fiveYears));
    }
}
