package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_toDuration {

    @Test
    public void test_toDuration() {
        for (int i = -20; i < 20; i++) {
            assertEquals(
                    Duration.ofMinutes(i),
                    Minutes.of(i).toDuration(),
                    "Minutes.of(" + i + ").toDuration() should equal Duration.ofMinutes(" + i + ")");
        }
    }
}
