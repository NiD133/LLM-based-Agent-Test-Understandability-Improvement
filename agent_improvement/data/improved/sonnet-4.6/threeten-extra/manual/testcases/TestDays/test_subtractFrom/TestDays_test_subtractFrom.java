package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestDays_test_subtractFrom {

    @Test
    public void test_subtractFrom_zeroDays_returnsUnchangedDate() {
        LocalDate date = LocalDate.of(2019, 1, 10);
        LocalDate result = (LocalDate) Days.of(0).subtractFrom(date);
        assertEquals(LocalDate.of(2019, 1, 10), result);
    }

    @Test
    public void test_subtractFrom_fiveDays_returnsDateFiveDaysEarlier() {
        LocalDate date = LocalDate.of(2019, 1, 10);
        LocalDate result = (LocalDate) Days.of(5).subtractFrom(date);
        assertEquals(LocalDate.of(2019, 1, 5), result);
    }
}
