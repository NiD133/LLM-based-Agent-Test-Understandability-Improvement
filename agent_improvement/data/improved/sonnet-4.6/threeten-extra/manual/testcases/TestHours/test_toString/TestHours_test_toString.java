package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_toString {

    // Hours.toString() produces ISO-8601 duration strings in the form "PTnH"
    @Test
    public void test_toString() {
        Hours positiveHours = Hours.of(5);
        assertEquals("PT5H", positiveHours.toString(),
                "Positive hours should be formatted as PT<n>H");

        Hours negativeHours = Hours.of(-1);
        assertEquals("PT-1H", negativeHours.toString(),
                "Negative hours should be formatted as PT-<n>H");
    }
}
