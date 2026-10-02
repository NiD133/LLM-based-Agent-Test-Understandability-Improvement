package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link Years#multipliedBy(int)} with a negative scalar.
 */
public class TestYears_test_multipliedBy_negate {

    @Test
    public void multipliedBy_negativeScalar_negatesAndScalesAmount() {
        Years fiveYears = Years.of(5);

        Years result = fiveYears.multipliedBy(-3);

        assertEquals(Years.of(-15), result);
    }
}
