package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Minutes#ofHours(int)} reports numeric overflow.
 */
public class TestMinutes_test_ofHours_overflow {

    @Test
    public void ofHours_throwsArithmeticException_whenHoursTimes60OverflowsInt() {
        // (Integer.MAX_VALUE / 60) + 60 hours converts to more than Integer.MAX_VALUE minutes,
        // so the internal "hours * 60" multiplication overflows an int.
        int hoursThatOverflowWhenConvertedToMinutes = (Integer.MAX_VALUE / 60) + 60;

        assertThrows(ArithmeticException.class,
                () -> Minutes.ofHours(hoursThatOverflowWhenConvertedToMinutes));
    }
}
