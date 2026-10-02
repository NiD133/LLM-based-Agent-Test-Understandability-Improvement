package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestMonths_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        LocalDate baseDate = LocalDate.of(2019, 1, 10);

        assertEquals(
                LocalDate.of(2019, 1, 10),
                Months.of(0).subtractFrom(baseDate));
        assertEquals(
                LocalDate.of(2018, 8, 10),
                Months.of(5).subtractFrom(baseDate));
    }
}
