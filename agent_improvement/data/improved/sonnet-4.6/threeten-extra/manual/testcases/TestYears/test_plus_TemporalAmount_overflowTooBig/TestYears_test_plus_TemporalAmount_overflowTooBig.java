package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_TemporalAmount_overflowTooBig {

    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        // Adding 2 to (MAX_VALUE - 1) pushes the sum past Integer.MAX_VALUE,
        // so Math.addExact inside Years.plus must throw ArithmeticException.
        Years nearMax = Years.of(Integer.MAX_VALUE - 1);
        Years addend = Years.of(2);

        assertThrows(ArithmeticException.class, () -> nearMax.plus(addend));
    }
}
