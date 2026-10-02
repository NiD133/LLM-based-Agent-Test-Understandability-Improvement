package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_from_P2W3D {

    @Test
    public void test_from_P2W3D() {
        assertEquals(Days.of(17), Days.from(new MockWeeksDays(2, 3)));
    }
}
