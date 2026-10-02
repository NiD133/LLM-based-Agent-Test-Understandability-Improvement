package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Minutes#toString()}.
 * <p>
 * {@code toString()} renders the amount as an ISO-8601 period in the
 * form {@code "PTnM"}, where {@code n} is the signed number of minutes.
 */
public class TestMinutes_test_toString {

    @Test
    public void toString_formatsPositiveMinutes() {
        Minutes fiveMinutes = Minutes.of(5);
        assertEquals("PT5M", fiveMinutes.toString());
    }

    @Test
    public void toString_formatsNegativeMinutes() {
        Minutes minusOneMinute = Minutes.of(-1);
        assertEquals("PT-1M", minusOneMinute.toString());
    }
}
