package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link BritishCutoverDate#atTime} rejects a null time argument.
 */
public class TestBritishCutoverChronology_test_atTime_null {

    @Test
    public void test_atTime_null() {
        BritishCutoverDate date = BritishCutoverDate.of(2014, 5, 26);

        // atTime must reject a null LocalTime rather than silently accept it.
        assertThrows(NullPointerException.class, () -> date.atTime(null));
    }
}
