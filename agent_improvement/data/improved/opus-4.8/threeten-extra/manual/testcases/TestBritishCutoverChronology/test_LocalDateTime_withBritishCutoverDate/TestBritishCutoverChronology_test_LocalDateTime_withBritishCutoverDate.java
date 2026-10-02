package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link LocalDateTime} can adjust its date part to a
 * {@link BritishCutoverDate} via {@link LocalDateTime#with}, while keeping the
 * time component untouched.
 */
public class TestBritishCutoverChronology_test_LocalDateTime_withBritishCutoverDate {

    @Test
    public void test_LocalDateTime_withBritishCutoverDate() {
        // Adjust LocalDateTime.MIN so its date matches the given British cutover date.
        BritishCutoverDate cutoverDate = BritishCutoverDate.of(2012, 6, 23);
        LocalDateTime adjusted = LocalDateTime.MIN.with(cutoverDate);

        // The date is taken from the adjuster; the time stays at start-of-day (00:00).
        assertEquals(LocalDateTime.of(2012, 6, 23, 0, 0), adjusted);
    }
}
