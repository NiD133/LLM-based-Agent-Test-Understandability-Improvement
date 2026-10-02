package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestYears_test_addTo {

    // A fixed base date used as the starting point in addTo scenarios
    private static final LocalDate BASE_DATE = LocalDate.of(2019, 1, 10);

    @Test
    public void test_addTo() {
        // Adding zero years should return the original date unchanged
        LocalDate expectedWhenZeroYearsAdded = LocalDate.of(2019, 1, 10);
        assertEquals(expectedWhenZeroYearsAdded, Years.of(0).addTo(BASE_DATE));

        // Adding five years should advance the date by exactly five years
        LocalDate expectedAfterFiveYears = LocalDate.of(2024, 1, 10);
        assertEquals(expectedAfterFiveYears, Years.of(5).addTo(BASE_DATE));
    }
}
