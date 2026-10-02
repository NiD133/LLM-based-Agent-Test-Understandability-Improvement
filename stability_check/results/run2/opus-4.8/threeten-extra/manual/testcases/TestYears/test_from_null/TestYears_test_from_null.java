package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#from(TemporalAmount)} rejects a null argument.
 */
public class TestYears_test_from_null {

    @Test
    public void from_nullTemporalAmount_throwsNullPointerException() {
        TemporalAmount nullAmount = null;
        assertThrows(NullPointerException.class, () -> Years.from(nullAmount));
    }
}
