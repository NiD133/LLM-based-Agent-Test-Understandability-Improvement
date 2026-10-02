package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestPaxChronology_test_adjust_toLocalDate {

    @Test
    public void test_adjust_toLocalDate() {
        PaxDate originalPaxDate = PaxDate.of(2000, 1, 4);
        LocalDate replacementIsoDate = LocalDate.of(2012, 7, 6);

        PaxDate adjustedPaxDate = originalPaxDate.with(replacementIsoDate);

        assertEquals(PaxDate.of(2012, 7, 27), adjustedPaxDate);
    }
}
