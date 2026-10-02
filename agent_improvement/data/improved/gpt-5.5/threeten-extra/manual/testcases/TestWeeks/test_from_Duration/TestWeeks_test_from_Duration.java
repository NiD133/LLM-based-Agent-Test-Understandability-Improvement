package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_from_Duration {

    @Test
    public void test_from_Duration() {
        Weeks expectedWeeks = Weeks.of(2);
        Duration durationOfTwoWeeks = Duration.ofDays(14);

        Weeks actualWeeks = Weeks.from(durationOfTwoWeeks);

        assertEquals(expectedWeeks, actualWeeks);
    }
}
