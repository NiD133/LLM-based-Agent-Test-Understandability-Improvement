package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.threeten.extra.TemporalFields.HALF_OF_YEAR;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Half#get(java.time.temporal.TemporalField)} for the
 * {@code HALF_OF_YEAR} field, which returns each half's numeric value.
 */
public class TestHalf_test_get {

    @Test
    public void get_halfOfYear_returnsNumericValueOfHalf() {
        assertEquals(1, Half.H1.get(HALF_OF_YEAR));
        assertEquals(2, Half.H2.get(HALF_OF_YEAR));
    }
}
