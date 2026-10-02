package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.threeten.extra.TemporalFields.HALF_OF_YEAR;

import org.junit.jupiter.api.Test;

public class TestHalf_test_getLong {

    @Test
    public void test_getLong() {
        assertEquals(1, Half.H1.getLong(HALF_OF_YEAR));
        assertEquals(2, Half.H2.getLong(HALF_OF_YEAR));
    }
}
