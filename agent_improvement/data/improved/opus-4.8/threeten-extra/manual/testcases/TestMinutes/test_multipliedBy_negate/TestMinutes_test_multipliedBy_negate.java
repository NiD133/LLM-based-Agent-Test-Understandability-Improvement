package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Minutes#multipliedBy(int)} multiplies the amount of
 * minutes by the given scalar, including when the scalar is negative.
 */
public class TestMinutes_test_multipliedBy_negate {

    @Test
    public void multipliedBy_negativeScalar_negatesAndScalesAmount() {
        Minutes fiveMinutes = Minutes.of(5);

        Minutes result = fiveMinutes.multipliedBy(-3);

        assertEquals(Minutes.of(-15), result);
    }
}
