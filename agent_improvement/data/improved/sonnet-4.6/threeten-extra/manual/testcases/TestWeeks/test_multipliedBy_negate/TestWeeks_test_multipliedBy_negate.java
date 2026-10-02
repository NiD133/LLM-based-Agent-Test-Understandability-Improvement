package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_multipliedBy_negate {

    @Test
    public void test_multipliedBy_negate() {
        Weeks fiveWeeks = Weeks.of(5);
        // 5 * -3 = -15: multiplying by a negative scalar negates and scales
        Weeks result = fiveWeeks.multipliedBy(-3);
        assertEquals(Weeks.of(-15), result);
    }
}
