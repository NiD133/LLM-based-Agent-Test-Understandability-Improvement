package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_from_P2W3D {

    @Test
    public void test_from_P2W3D() {
        // 2 weeks (14 days) + 3 days = 17 days
        int expectedDays = 2 * 7 + 3;
        assertEquals(Days.of(expectedDays), Days.from(new MockWeeksDays(2, 3)));
    }
}
