package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        assertWeeksSubtracted(
                0,
                LocalDate.of(2019, 1, 10),
                LocalDate.of(2019, 1, 10));
        assertWeeksSubtracted(
                5,
                LocalDate.of(2019, 1, 10),
                LocalDate.of(2018, 12, 6));
    }

    private static void assertWeeksSubtracted(int weeks, LocalDate date, LocalDate expectedDate) {
        Temporal actualDate = Weeks.of(weeks).subtractFrom(date);
        assertEquals(expectedDate, actualDate);
    }
}
