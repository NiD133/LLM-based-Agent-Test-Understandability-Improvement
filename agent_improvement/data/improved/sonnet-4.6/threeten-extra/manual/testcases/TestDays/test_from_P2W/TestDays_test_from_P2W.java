package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_from_P2W {

    @Test
    public void test_from_P2W() {
        // 2 weeks with 0 extra days should convert to 14 days (2 * 7)
        assertEquals(Days.of(14), Days.from(new MockWeeksDays(2, 0)));
    }
}
