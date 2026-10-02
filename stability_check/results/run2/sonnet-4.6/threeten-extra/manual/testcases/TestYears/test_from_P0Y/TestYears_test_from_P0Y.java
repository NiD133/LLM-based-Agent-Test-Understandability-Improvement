package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_from_P0Y {

    // P0Y is the ISO-8601 representation of a zero-year period
    @Test
    public void test_from_P0Y() {
        Years expected = Years.of(0);
        Years actual = Years.from(Period.ofYears(0));
        assertEquals(expected, actual);
    }
}
