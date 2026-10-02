package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#addTo(java.time.temporal.Temporal)}.
 * <p>
 * {@code addTo} returns a copy of the supplied temporal with this many months
 * added; adding zero months must leave the date unchanged.
 */
public class TestMonths_test_addTo {

    @Test
    public void test_addTo() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);

        // Adding zero months leaves the date unchanged.
        assertEquals(LocalDate.of(2019, 1, 10), Months.of(0).addTo(startDate));

        // Adding 5 months moves January 10th to June 10th of the same year.
        assertEquals(LocalDate.of(2019, 6, 10), Months.of(5).addTo(startDate));
    }
}
