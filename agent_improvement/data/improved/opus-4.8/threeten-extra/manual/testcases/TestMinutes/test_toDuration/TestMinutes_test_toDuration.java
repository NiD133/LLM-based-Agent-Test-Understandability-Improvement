package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Minutes#toDuration()}.
 */
public class TestMinutes_test_toDuration {

    /**
     * {@code toDuration()} should yield a {@link Duration} holding exactly the
     * same number of minutes, for positive, zero and negative amounts alike.
     */
    @Test
    public void test_toDuration() {
        for (int minutes = -20; minutes < 20; minutes++) {
            Duration expected = Duration.ofMinutes(minutes);
            Duration actual = Minutes.of(minutes).toDuration();
            assertEquals(expected, actual);
        }
    }
}
