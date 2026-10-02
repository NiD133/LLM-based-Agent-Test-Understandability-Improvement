package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#addTo(java.time.temporal.Temporal)}.
 */
public class TestDays_test_addTo {

    @Test
    public void test_addTo() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);

        // Adding zero days leaves the date unchanged.
        assertEquals(LocalDate.of(2019, 1, 10), Days.of(0).addTo(startDate));

        // Adding five days advances the date by five days.
        assertEquals(LocalDate.of(2019, 1, 15), Days.of(5).addTo(startDate));
    }
}
