package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Seconds#multipliedBy(int)} works with a negative scalar.
 */
public class TestSeconds_test_multipliedBy_negate {

    @Test
    public void multipliedBy_negativeScalar_negatesAndScalesTheAmount() {
        Seconds fiveSeconds = Seconds.of(5);

        Seconds result = fiveSeconds.multipliedBy(-3);

        assertEquals(Seconds.of(-15), result);
    }
}
