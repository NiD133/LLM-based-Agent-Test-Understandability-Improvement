package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_between {

    @Test
    public void test_between() {
        // 2019-01-01 to 2021-01-01 spans exactly 2 years = 104 weeks
        LocalDate start = LocalDate.of(2019, 1, 1);
        LocalDate end = LocalDate.of(2021, 1, 1);

        assertEquals(Weeks.of(104), Weeks.between(start, end));
    }
}
