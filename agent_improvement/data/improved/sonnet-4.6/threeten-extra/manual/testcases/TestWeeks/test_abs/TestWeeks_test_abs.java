package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_abs {

    @Test
    public void test_abs() {
        // Zero remains zero
        assertEquals(Weeks.of(0), Weeks.of(0).abs());

        // Positive value is unchanged
        assertEquals(Weeks.of(12), Weeks.of(12).abs());

        // Negative value becomes its positive counterpart
        assertEquals(Weeks.of(12), Weeks.of(-12).abs());

        // Boundary: Integer.MAX_VALUE is already positive and stays the same
        assertEquals(Weeks.of(Integer.MAX_VALUE), Weeks.of(Integer.MAX_VALUE).abs());

        // Boundary: negated Integer.MAX_VALUE becomes Integer.MAX_VALUE
        assertEquals(Weeks.of(Integer.MAX_VALUE), Weeks.of(-Integer.MAX_VALUE).abs());
    }
}
