package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TestHalf_test_from_TemporalAccessor {

    // Half.from(TemporalAccessor) extracts HALF_OF_YEAR from any ISO temporal:
    // January–June → H1, July–December → H2.
    @Test
    public void test_from_TemporalAccessor() {
        // July 6, 2011 falls in the second half of the year
        assertEquals(Half.H2, Half.from(LocalDate.of(2011, 7, 6)));
        // February 3, 2012 falls in the first half of the year
        assertEquals(Half.H1, Half.from(LocalDateTime.of(2012, 2, 3, 12, 30)));
    }
}
