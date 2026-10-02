package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_from_P2W {

    @Test
    public void test_from_P2W() {
        // Weeks.from should convert a Period of 2 weeks into Weeks.of(2)
        assertEquals(Weeks.of(2), Weeks.from(Period.ofWeeks(2)));
    }
}
