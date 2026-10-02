package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_between {

    @Test
    public void test_between() {
        assertEquals(
                Weeks.of(104),
                Weeks.between(
                        LocalDate.of(2019, 1, 1),
                        LocalDate.of(2021, 1, 1)));
    }
}
