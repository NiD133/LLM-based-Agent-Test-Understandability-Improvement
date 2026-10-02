package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Half#from(java.time.temporal.TemporalAccessor)}, which derives the
 * half-of-year from any temporal that can be resolved to an ISO date.
 */
public class TestHalf_test_from_TemporalAccessor {

    @Test
    public void test_from_TemporalAccessor() {
        // July falls in the second half of the year (July-December).
        assertEquals(Half.H2, Half.from(LocalDate.of(2011, 7, 6)));
        // February falls in the first half of the year (January-June).
        assertEquals(Half.H1, Half.from(LocalDateTime.of(2012, 2, 3, 12, 30)));
    }
}
