package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AmPm#of(int)} maps each valid int value back to an
 * {@code AmPm} whose {@link AmPm#getValue()} equals that same int.
 */
public class TestAmPm_test_of_int_singleton_equals {

    /**
     * For every valid AM/PM value (0 for AM, 1 for PM), {@code AmPm.of(value)}
     * must return an instance whose {@code getValue()} round-trips to the value.
     */
    @Test
    public void of_int_returnsInstanceWhoseValueMatchesInput() {
        for (int amPmValue = 0; amPmValue <= 1; amPmValue++) {
            AmPm amPm = AmPm.of(amPmValue);

            assertEquals(amPmValue, amPm.getValue());
        }
    }
}
