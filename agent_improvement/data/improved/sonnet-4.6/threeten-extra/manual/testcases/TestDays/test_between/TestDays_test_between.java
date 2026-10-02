package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestDays_test_between {

    // 2019 is a common year (365 days) and 2020 is a leap year (366 days)
    private static final int DAYS_IN_2019 = 365;
    private static final int DAYS_IN_2020 = 366;

    @Test
    public void test_between() {
        LocalDate start = LocalDate.of(2019, 1, 1);
        LocalDate end   = LocalDate.of(2021, 1, 1);

        Days expected = Days.of(DAYS_IN_2019 + DAYS_IN_2020);
        Days actual   = Days.between(start, end);

        assertEquals(expected, actual);
    }
}
