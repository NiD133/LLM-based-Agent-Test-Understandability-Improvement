package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestYears_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        LocalDate originalDate = LocalDate.of(2019, 1, 10);
        assertEquals(originalDate, Years.of(0).subtractFrom(LocalDate.of(2019, 1, 10)));

        LocalDate fiveYearsEarlier = LocalDate.of(2014, 1, 10);
        assertEquals(fiveYearsEarlier, Years.of(5).subtractFrom(LocalDate.of(2019, 1, 10)));
    }
}
