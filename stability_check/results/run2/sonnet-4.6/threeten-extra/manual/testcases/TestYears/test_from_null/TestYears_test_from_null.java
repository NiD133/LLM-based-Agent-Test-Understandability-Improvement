package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

public class TestYears_test_from_null {

    /**
     * Verifies that {@link Years#from(TemporalAmount)} throws {@link NullPointerException}
     * when given a null argument, as required by its "not null" contract.
     */
    @Test
    public void test_from_null() {
        assertThrows(NullPointerException.class, () -> Years.from((TemporalAmount) null));
    }
}
