package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_LocalDateTime_withBritishCutoverDate {

    @Test
    public void test_LocalDateTime_withBritishCutoverDate() {
        // Use a BritishCutoverDate as a TemporalAdjuster to set the date portion of a LocalDateTime.
        // LocalDateTime.MIN starts at year -999999999, but .with(cutover) replaces the date fields
        // with those from the BritishCutoverDate, keeping the time at 00:00.
        BritishCutoverDate cutover = BritishCutoverDate.of(2012, 6, 23);
        LocalDateTime test = LocalDateTime.MIN.with(cutover);
        assertEquals(LocalDateTime.of(2012, 6, 23, 0, 0), test);
    }
}
