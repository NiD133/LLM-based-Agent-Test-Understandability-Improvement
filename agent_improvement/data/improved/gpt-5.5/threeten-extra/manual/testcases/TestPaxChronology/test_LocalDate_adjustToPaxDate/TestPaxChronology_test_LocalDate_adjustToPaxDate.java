package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestPaxChronology_test_LocalDate_adjustToPaxDate {

    @Test
    public void test_LocalDate_adjustToPaxDate() {
        PaxDate paxDate = PaxDate.of(2012, 6, 23);

        LocalDate adjustedIsoDate = LocalDate.MIN.with(paxDate);

        assertEquals(LocalDate.of(2012, 6, 4), adjustedIsoDate);
    }
}
