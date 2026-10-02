package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_from_P0Y {

    @Test
    public void test_from_P0Y() {
        Years expectedZeroYears = Years.of(0);
        Years actualYears = Years.from(Period.ofYears(0));

        assertEquals(expectedZeroYears, actualYears);
    }
}
