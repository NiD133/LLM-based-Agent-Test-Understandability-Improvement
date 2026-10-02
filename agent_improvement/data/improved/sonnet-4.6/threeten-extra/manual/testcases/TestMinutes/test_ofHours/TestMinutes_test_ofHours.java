package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_ofHours {

    // The largest number of whole hours that fits in an int when converted to minutes
    private static final int MAX_WHOLE_HOURS = Integer.MAX_VALUE / 60;
    // The smallest (most negative) number of whole hours that fits in an int when converted to minutes
    private static final int MIN_WHOLE_HOURS = Integer.MIN_VALUE / 60;

    @Test
    public void test_ofHours() {
        // Zero hours yields zero minutes
        assertEquals(0, Minutes.ofHours(0).getAmount());

        // Positive hours: each hour contributes 60 minutes
        assertEquals(60, Minutes.ofHours(1).getAmount());
        assertEquals(120, Minutes.ofHours(2).getAmount());

        // Boundary: largest positive hour value that avoids int overflow on multiplication
        assertEquals(MAX_WHOLE_HOURS * 60, Minutes.ofHours(MAX_WHOLE_HOURS).getAmount());

        // Negative hours: result is the negation of the positive equivalent
        assertEquals(-60, Minutes.ofHours(-1).getAmount());
        assertEquals(-120, Minutes.ofHours(-2).getAmount());

        // Boundary: largest negative hour value that avoids int overflow on multiplication
        assertEquals(MIN_WHOLE_HOURS * 60, Minutes.ofHours(MIN_WHOLE_HOURS).getAmount());
    }
}
