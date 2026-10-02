package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_adjust_toLocalDate {

    @Test
    public void test_adjust_toLocalDate() {
        InternationalFixedDate baseDate = InternationalFixedDate.of(2000, 1, 4);
        LocalDate replacementIsoDate = LocalDate.of(2012, 7, 6);

        InternationalFixedDate adjustedDate = baseDate.with(replacementIsoDate);

        assertEquals(InternationalFixedDate.of(2012, 7, 19), adjustedDate);
    }
}
