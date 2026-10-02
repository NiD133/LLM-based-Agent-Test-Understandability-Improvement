package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestYears_test_between {

    @Test
    public void test_between() {
        LocalDate startInclusive = LocalDate.of(2019, 1, 1);
        LocalDate endExclusive = LocalDate.of(2021, 1, 1);

        Years actual = Years.between(startInclusive, endExclusive);

        assertEquals(Years.of(2), actual);
    }
}
