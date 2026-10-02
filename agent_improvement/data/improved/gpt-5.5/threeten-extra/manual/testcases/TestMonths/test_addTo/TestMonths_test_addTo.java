package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestMonths_test_addTo {

    @Test
    public void test_addTo() {
        assertEquals(
                LocalDate.of(2019, 1, 10),
                Months.of(0).addTo(LocalDate.of(2019, 1, 10)),
                "Adding zero months should leave the date unchanged");

        assertEquals(
                LocalDate.of(2019, 6, 10),
                Months.of(5).addTo(LocalDate.of(2019, 1, 10)),
                "Adding five months should move the date forward by five months");
    }
}
