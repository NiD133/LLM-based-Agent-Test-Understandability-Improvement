package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hours#toString()} renders the amount using the
 * ISO-8601 'PTnH' format, including the sign for negative amounts.
 */
public class TestHours_test_toString {

    @Test
    public void toString_formatsHoursUsingIso8601() {
        Hours fiveHours = Hours.of(5);
        assertEquals("PT5H", fiveHours.toString());

        Hours minusOneHour = Hours.of(-1);
        assertEquals("PT-1H", minusOneHour.toString());
    }
}
