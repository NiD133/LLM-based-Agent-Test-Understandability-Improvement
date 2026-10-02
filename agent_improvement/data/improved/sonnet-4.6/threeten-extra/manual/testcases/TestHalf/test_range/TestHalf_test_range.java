package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.threeten.extra.TemporalFields.HALF_OF_YEAR;

import org.junit.jupiter.api.Test;

public class TestHalf_test_range {

    // Verifies that querying the range of HALF_OF_YEAR on any Half instance
    // returns the same ValueRange as the field's own range (1 to 2).
    @Test
    public void test_range() {
        assertEquals(HALF_OF_YEAR.range(), Half.H1.range(HALF_OF_YEAR));
    }
}
