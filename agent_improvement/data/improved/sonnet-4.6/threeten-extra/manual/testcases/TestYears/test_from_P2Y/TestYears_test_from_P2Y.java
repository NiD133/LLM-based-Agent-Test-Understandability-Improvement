package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_from_P2Y {

    @Test
    public void test_from_P2Y() {
        // Period.ofYears(2) represents "P2Y"; Years.from should convert it to Years.of(2)
        Years expected = Years.of(2);
        Years actual = Years.from(Period.ofYears(2));
        assertEquals(expected, actual);
    }
}
