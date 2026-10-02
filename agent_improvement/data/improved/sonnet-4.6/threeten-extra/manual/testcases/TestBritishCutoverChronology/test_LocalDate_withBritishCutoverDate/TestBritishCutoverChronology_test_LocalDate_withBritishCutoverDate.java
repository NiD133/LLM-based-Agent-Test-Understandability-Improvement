package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_LocalDate_withBritishCutoverDate {

    // Verifies that a LocalDate adjusted with a BritishCutoverDate yields the
    // equivalent ISO local date (post-cutover dates map 1-to-1 to ISO dates).
    @Test
    public void test_LocalDate_withBritishCutoverDate() {
        BritishCutoverDate cutover = BritishCutoverDate.of(2012, 6, 23);
        LocalDate test = LocalDate.MIN.with(cutover);
        assertEquals(LocalDate.of(2012, 6, 23), test);
    }
}
