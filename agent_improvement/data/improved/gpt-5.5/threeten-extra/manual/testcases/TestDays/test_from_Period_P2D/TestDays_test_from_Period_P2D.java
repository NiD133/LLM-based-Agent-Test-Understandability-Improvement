package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestDays_test_from_Period_P2D {

    @Test
    public void test_from_Period_P2D() {
        Period twoDayPeriod = Period.ofDays(2);

        Days actualDays = Days.from(twoDayPeriod);

        assertEquals(Days.of(2), actualDays);
    }
}
