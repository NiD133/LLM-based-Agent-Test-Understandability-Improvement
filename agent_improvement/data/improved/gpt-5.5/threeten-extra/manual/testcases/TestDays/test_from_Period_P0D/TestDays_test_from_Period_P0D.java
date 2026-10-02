package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestDays_test_from_Period_P0D {

    @Test
    public void test_from_Period_P0D() {
        assertEquals(Days.of(0), Days.from(Period.ofDays(0)));
    }
}
