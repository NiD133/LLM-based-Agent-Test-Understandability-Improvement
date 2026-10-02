package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_addTo {

    @Test
    public void test_addTo_zeroWeeks_returnsUnchangedDate() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);
        LocalDate result = (LocalDate) Weeks.of(0).addTo(startDate);
        assertEquals(startDate, result);
    }

    @Test
    public void test_addTo_fiveWeeks_advancesDateByFiveWeeks() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);
        LocalDate expectedDate = LocalDate.of(2019, 2, 14);
        LocalDate result = (LocalDate) Weeks.of(5).addTo(startDate);
        assertEquals(expectedDate, result);
    }
}
