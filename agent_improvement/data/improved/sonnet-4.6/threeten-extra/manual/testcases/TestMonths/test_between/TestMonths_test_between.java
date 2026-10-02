package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestMonths_test_between {

    @Test
    public void test_between() {
        LocalDate startDate = LocalDate.of(2019, 1, 1);
        LocalDate endDate = LocalDate.of(2021, 1, 1);
        // 2019-01-01 to 2021-01-01 spans exactly 2 years, i.e. 24 months
        Months expected = Months.of(24);

        assertEquals(expected, Months.between(startDate, endDate));
    }
}
