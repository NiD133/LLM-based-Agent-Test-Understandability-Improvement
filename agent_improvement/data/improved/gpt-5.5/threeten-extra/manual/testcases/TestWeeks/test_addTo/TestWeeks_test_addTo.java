package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_addTo {

    @Test
    public void test_addTo() {
        assertEquals(LocalDate.of(2019, 1, 10), Weeks.of(0).addTo(LocalDate.of(2019, 1, 10)));
        assertEquals(LocalDate.of(2019, 2, 14), Weeks.of(5).addTo(LocalDate.of(2019, 1, 10)));
    }
}
