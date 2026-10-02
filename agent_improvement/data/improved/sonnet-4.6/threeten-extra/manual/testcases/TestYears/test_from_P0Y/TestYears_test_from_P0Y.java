package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_from_P0Y {

    // Verifies that converting a zero-year Period via Years.from returns the same value as Years.of(0)
    @Test
    public void test_from_P0Y() {
        assertEquals(Years.of(0), Years.from(Period.ofYears(0)));
    }
}
