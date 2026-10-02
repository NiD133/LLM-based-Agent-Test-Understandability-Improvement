package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_negated_overflow {

    @Test
    public void test_negated_overflow() {
        Weeks minimumWeeks = Weeks.of(Integer.MIN_VALUE);

        assertThrows(ArithmeticException.class, () -> minimumWeeks.negated());
    }
}
