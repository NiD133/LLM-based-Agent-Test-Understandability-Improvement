package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#between(java.time.temporal.Temporal, java.time.temporal.Temporal)}.
 */
public class TestYears_test_between {

    @Test
    public void between_returnsWholeYearsFromStartToEnd() {
        LocalDate start = LocalDate.of(2019, 1, 1);
        LocalDate end = LocalDate.of(2021, 1, 1);

        Years yearsBetween = Years.between(start, end);

        assertEquals(Years.of(2), yearsBetween);
    }
}
