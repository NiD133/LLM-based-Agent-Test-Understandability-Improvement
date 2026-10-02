package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestMonths_test_between {

    @Test
    public void test_between() {
        Months expectedMonths = Months.of(24);
        Months actualMonths = Months.between(LocalDate.of(2019, 1, 1), LocalDate.of(2021, 1, 1));

        assertEquals(expectedMonths, actualMonths);
    }
}
