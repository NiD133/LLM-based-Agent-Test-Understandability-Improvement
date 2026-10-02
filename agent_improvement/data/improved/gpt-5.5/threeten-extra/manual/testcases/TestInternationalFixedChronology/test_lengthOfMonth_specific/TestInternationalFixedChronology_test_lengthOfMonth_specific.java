package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_lengthOfMonth_specific {

    @Test
    public void test_lengthOfMonth_specific() {
        assertLengthOfMonth(29, InternationalFixedDate.of(1900, 13, 29));
        assertLengthOfMonth(29, InternationalFixedDate.of(2000, 13, 29));
        assertLengthOfMonth(29, InternationalFixedDate.of(2000, 6, 29));
    }

    private void assertLengthOfMonth(int expectedLength, InternationalFixedDate date) {
        assertEquals(expectedLength, date.lengthOfMonth());
    }
}
