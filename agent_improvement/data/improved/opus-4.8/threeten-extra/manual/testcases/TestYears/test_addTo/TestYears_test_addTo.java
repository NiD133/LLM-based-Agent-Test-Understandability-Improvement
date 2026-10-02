package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#addTo(java.time.temporal.Temporal)}, which adds the number
 * of years held by a {@code Years} instance to a temporal such as a date.
 */
public class TestYears_test_addTo {

    @Test
    public void addingZeroYears_leavesTheDateUnchanged() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);

        LocalDate result = (LocalDate) Years.of(0).addTo(startDate);

        assertEquals(LocalDate.of(2019, 1, 10), result);
    }

    @Test
    public void addingFiveYears_advancesTheDateByFiveYears() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);

        LocalDate result = (LocalDate) Years.of(5).addTo(startDate);

        assertEquals(LocalDate.of(2024, 1, 10), result);
    }
}
