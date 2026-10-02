package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#addTo(java.time.temporal.Temporal)}, which adds the
 * weeks held by a {@code Weeks} instance onto a temporal such as a date.
 */
public class TestWeeks_test_addTo {

    @Test
    public void addTo_addsTheNumberOfWeeksToTheDate() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);

        // Adding zero weeks leaves the date unchanged.
        assertEquals(LocalDate.of(2019, 1, 10), Weeks.of(0).addTo(startDate));

        // Adding 5 weeks (35 days) moves the date forward from 10 Jan to 14 Feb.
        assertEquals(LocalDate.of(2019, 2, 14), Weeks.of(5).addTo(startDate));
    }
}
