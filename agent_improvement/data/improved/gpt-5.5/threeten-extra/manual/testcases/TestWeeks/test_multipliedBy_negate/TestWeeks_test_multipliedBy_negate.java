package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_multipliedBy_negate {

    @Test
    public void test_multipliedBy_negate() {
        Weeks fiveWeeks = Weeks.of(5);
        Weeks expectedWeeks = Weeks.of(-15);

        Weeks actualWeeks = fiveWeeks.multipliedBy(-3);

        assertEquals(expectedWeeks, actualWeeks);
    }
}
