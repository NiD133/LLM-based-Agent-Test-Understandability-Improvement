package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Seconds#toString()} produces the ISO-8601 "PTnS" format,
 * where n is the (possibly negative) number of seconds.
 */
public class TestSeconds_test_toString {

    @Test
    public void toString_formatsPositiveAndNegativeAmounts() {
        // A positive amount renders as "PT<n>S".
        assertEquals("PT5S", Seconds.of(5).toString());

        // A negative amount keeps the sign inside the value: "PT-<n>S".
        assertEquals("PT-1S", Seconds.of(-1).toString());
    }
}
