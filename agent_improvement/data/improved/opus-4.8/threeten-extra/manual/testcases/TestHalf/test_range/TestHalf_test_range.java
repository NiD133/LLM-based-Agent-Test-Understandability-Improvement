package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.threeten.extra.TemporalFields.HALF_OF_YEAR;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Half#range(java.time.temporal.TemporalField)}.
 */
public class TestHalf_test_range {

    @Test
    public void range_forHalfOfYearField_returnsFieldsOwnRange() {
        // Half.range(HALF_OF_YEAR) should delegate to the field's own range (1..2).
        assertEquals(HALF_OF_YEAR.range(), Half.H1.range(HALF_OF_YEAR));
    }
}
