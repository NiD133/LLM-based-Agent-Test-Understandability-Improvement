package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_subtractFrom {

    @Test
    public void test_subtractFrom_zeroWeeks_returnsUnchangedDate() {
        LocalDate date = LocalDate.of(2019, 1, 10);
        LocalDate result = (LocalDate) Weeks.of(0).subtractFrom(date);
        assertEquals(date, result);
    }

    @Test
    public void test_subtractFrom_fiveWeeks_returnsDateFiveWeeksEarlier() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);
        LocalDate expectedDate = LocalDate.of(2018, 12, 6);
        LocalDate result = (LocalDate) Weeks.of(5).subtractFrom(startDate);
        assertEquals(expectedDate, result);
    }
}
