package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_LocalDate_withBritishCutoverDate {

    @Test
    public void test_LocalDate_withBritishCutoverDate() {
        BritishCutoverDate britishCutoverDate = BritishCutoverDate.of(2012, 6, 23);

        LocalDate convertedDate = LocalDate.MIN.with(britishCutoverDate);

        assertEquals(LocalDate.of(2012, 6, 23), convertedDate);
    }
}
