package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestDays_test_from_Duration {

    @Test
    public void test_from_Duration() {
        Duration twoDayDuration = Duration.ofDays(2);
        Days expectedDays = Days.of(2);

        assertEquals(expectedDays, Days.from(twoDayDuration));
    }
}
