package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_toString {

    @Test
    public void test_toString() {
        assertToString(5, "PT5M");
        assertToString(-1, "PT-1M");
    }

    private static void assertToString(int minutes, String expectedText) {
        Minutes test = Minutes.of(minutes);
        assertEquals(expectedText, test.toString());
    }
}
