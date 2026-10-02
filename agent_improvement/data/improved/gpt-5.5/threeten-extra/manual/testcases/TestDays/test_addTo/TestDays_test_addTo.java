package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestDays_test_addTo {

    @Test
    public void test_addTo() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);

        assertEquals(startDate, Days.of(0).addTo(startDate));
        assertEquals(LocalDate.of(2019, 1, 15), Days.of(5).addTo(startDate));
    }
}
