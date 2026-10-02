package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Seconds#ofMinutes(int)} rejects inputs whose conversion
 * to seconds would overflow the {@code int} range.
 */
public class TestSeconds_test_ofMinutes_overflow {

    @Test
    public void ofMinutes_throwsArithmeticException_whenSecondsOverflowInt() {
        // A minute count this large maps to more than Integer.MAX_VALUE seconds.
        int minutesThatOverflow = (Integer.MAX_VALUE / 60) + 60;

        assertThrows(ArithmeticException.class, () -> Seconds.ofMinutes(minutesThatOverflow));
    }
}
