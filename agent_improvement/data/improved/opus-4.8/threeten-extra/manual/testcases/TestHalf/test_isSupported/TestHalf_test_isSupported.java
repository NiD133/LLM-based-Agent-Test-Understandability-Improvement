package org.threeten.extra;

import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.threeten.extra.TemporalFields.HALF_OF_YEAR;

import java.time.temporal.ChronoField;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Half#isSupported(java.time.temporal.TemporalField)}.
 * <p>
 * The only field a {@code Half} supports is {@link TemporalFields#HALF_OF_YEAR}.
 * A {@code null} field, every {@code ChronoField}, and any other unrelated
 * temporal field (such as {@code QUARTER_OF_YEAR}) are all unsupported.
 */
public class TestHalf_test_isSupported {

    @Test
    public void test_isSupported() {
        Half test = Half.H1;

        // The half-of-year field is the single supported field.
        assertTrue(test.isSupported(HALF_OF_YEAR));

        // A null field is never supported.
        assertFalse(test.isSupported(null));

        // No ChronoField is supported by a Half.
        for (ChronoField field : ChronoField.values()) {
            assertFalse(test.isSupported(field), "ChronoField should be unsupported: " + field);
        }

        // An unrelated non-ChronoField field is also unsupported.
        assertFalse(test.isSupported(QUARTER_OF_YEAR));
    }
}
