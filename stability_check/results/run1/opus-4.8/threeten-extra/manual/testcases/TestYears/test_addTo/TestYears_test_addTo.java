package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#addTo(java.time.temporal.Temporal)}.
 */
public class TestYears_test_addTo {

    @Test
    public void test_addTo() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);

        // Adding zero years leaves the date unchanged.
        assertEquals(LocalDate.of(2019, 1, 10), Years.of(0).addTo(startDate));

        // Adding five years advances the date by five years.
        assertEquals(LocalDate.of(2024, 1, 10), Years.of(5).addTo(startDate));
    }
}
