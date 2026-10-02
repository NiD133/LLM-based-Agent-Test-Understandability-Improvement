package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_from_Duration {

    @Test
    public void test_from_Duration() {
        // 14 days is exactly 2 weeks, so Weeks.from should convert it without remainder
        Duration twoWeeksAsDays = Duration.ofDays(14);
        assertEquals(Weeks.of(2), Weeks.from(twoWeeksAsDays));
    }
}
