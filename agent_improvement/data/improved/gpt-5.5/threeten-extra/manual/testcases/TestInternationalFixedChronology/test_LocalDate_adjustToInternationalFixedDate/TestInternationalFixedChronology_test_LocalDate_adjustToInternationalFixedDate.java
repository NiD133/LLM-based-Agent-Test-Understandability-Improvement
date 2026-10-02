package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_LocalDate_adjustToInternationalFixedDate {

    @Test
    public void test_LocalDate_adjustToInternationalFixedDate() {
        InternationalFixedDate internationalFixedDate = InternationalFixedDate.of(2012, 7, 19);
        LocalDate adjustedDate = LocalDate.MIN.with(internationalFixedDate);

        assertEquals(LocalDate.of(2012, 7, 6), adjustedDate);
    }
}
