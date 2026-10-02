package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestDays_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        assertSubtractFrom(
                LocalDate.of(2019, 1, 10),
                Days.of(0).subtractFrom(LocalDate.of(2019, 1, 10)));
        assertSubtractFrom(
                LocalDate.of(2019, 1, 5),
                Days.of(5).subtractFrom(LocalDate.of(2019, 1, 10)));
    }

    private static void assertSubtractFrom(LocalDate expectedDate, Object actualDate) {
        assertEquals(expectedDate, actualDate);
    }
}
