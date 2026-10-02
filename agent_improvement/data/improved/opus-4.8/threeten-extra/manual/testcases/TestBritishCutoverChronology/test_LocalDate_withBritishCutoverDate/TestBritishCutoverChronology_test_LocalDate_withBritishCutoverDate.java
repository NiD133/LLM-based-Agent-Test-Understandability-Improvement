package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link LocalDate} can adjust itself onto a {@link BritishCutoverDate}.
 */
public class TestBritishCutoverChronology_test_LocalDate_withBritishCutoverDate {

    /**
     * Adjusting a LocalDate with a BritishCutoverDate should yield the equivalent
     * ISO LocalDate for that British cutover day (2012-06-23 is past the cutover,
     * so the ISO and British representations coincide).
     */
    @Test
    public void test_LocalDate_withBritishCutoverDate() {
        BritishCutoverDate cutoverDate = BritishCutoverDate.of(2012, 6, 23);

        LocalDate adjusted = LocalDate.MIN.with(cutoverDate);

        assertEquals(LocalDate.of(2012, 6, 23), adjusted);
    }
}
