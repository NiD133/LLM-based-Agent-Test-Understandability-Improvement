package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestDays_test_addTo {

    @Test
    public void test_addTo() {
        LocalDate baseDate = LocalDate.of(2019, 1, 10);

        // Adding zero days leaves the date unchanged
        LocalDate expectedUnchanged = LocalDate.of(2019, 1, 10);
        assertEquals(expectedUnchanged, Days.of(0).addTo(baseDate));

        // Adding five days advances the date by exactly five days
        LocalDate expectedAfterFiveDays = LocalDate.of(2019, 1, 15);
        assertEquals(expectedAfterFiveDays, Days.of(5).addTo(baseDate));
    }
}
