package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#between(java.time.temporal.Temporal, java.time.temporal.Temporal)}.
 */
public class TestWeeks_test_between {

    @Test
    public void between_twoDates_returnsWholeWeeksFromStartToEnd() {
        LocalDate start = LocalDate.of(2019, 1, 1);
        LocalDate end = LocalDate.of(2021, 1, 1);

        // The two-year span (2019-01-01 to 2021-01-01) covers exactly 104 whole weeks.
        Weeks weeksBetween = Weeks.between(start, end);

        assertEquals(Weeks.of(104), weeksBetween);
    }
}
