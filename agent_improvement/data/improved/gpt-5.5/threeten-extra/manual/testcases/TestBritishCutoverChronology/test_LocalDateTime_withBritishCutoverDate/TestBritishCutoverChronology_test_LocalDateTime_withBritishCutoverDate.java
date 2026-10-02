package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_LocalDateTime_withBritishCutoverDate {

    @Test
    public void test_LocalDateTime_withBritishCutoverDate() {
        BritishCutoverDate britishCutoverDate = BritishCutoverDate.of(2012, 6, 23);

        LocalDateTime adjustedDateTime = LocalDateTime.MIN.with(britishCutoverDate);

        assertEquals(LocalDateTime.of(2012, 6, 23, 0, 0), adjustedDateTime);
    }
}
