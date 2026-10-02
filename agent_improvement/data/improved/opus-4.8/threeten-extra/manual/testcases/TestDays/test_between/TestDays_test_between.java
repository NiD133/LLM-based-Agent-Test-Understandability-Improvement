package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#between(java.time.temporal.Temporal, java.time.temporal.Temporal)}.
 */
public class TestDays_test_between {

    @Test
    public void between_countsWholeDaysFromStartInclusiveToEndExclusive() {
        // 2019 is a common year (365 days), 2020 is a leap year (366 days),
        // so the span from 2019-01-01 (inclusive) to 2021-01-01 (exclusive) is 365 + 366 days.
        LocalDate start = LocalDate.of(2019, 1, 1);
        LocalDate end = LocalDate.of(2021, 1, 1);
        Days expected = Days.of(365 + 366);

        assertEquals(expected, Days.between(start, end));
    }
}
