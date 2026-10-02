package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestYears_test_addTo {

    @Test
    public void test_addTo_zeroYears_returnsUnchangedDate() {
        LocalDate originalDate = LocalDate.of(2019, 1, 10);
        LocalDate result = (LocalDate) Years.of(0).addTo(originalDate);
        assertEquals(originalDate, result);
    }

    @Test
    public void test_addTo_positiveYears_advancesDateByThatManyYears() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);
        LocalDate expectedDate = LocalDate.of(2024, 1, 10);
        LocalDate result = (LocalDate) Years.of(5).addTo(startDate);
        assertEquals(expectedDate, result);
    }
}
