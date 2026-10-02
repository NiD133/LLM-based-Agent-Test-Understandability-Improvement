package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestDays_test_between {

    @Test
    public void test_between() {
        Days expectedDaysBetweenDates = Days.of(365 + 366);
        Days actualDaysBetweenDates = Days.between(
                LocalDate.of(2019, 1, 1),
                LocalDate.of(2021, 1, 1));

        assertEquals(expectedDaysBetweenDates, actualDaysBetweenDates);
    }
}
