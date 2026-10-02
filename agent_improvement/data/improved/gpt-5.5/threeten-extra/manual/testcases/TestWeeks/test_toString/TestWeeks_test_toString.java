package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_toString {

    @Test
    public void test_toString() {
        Weeks fiveWeeks = Weeks.of(5);
        assertEquals("P5W", fiveWeeks.toString());

        Weeks negativeOneWeek = Weeks.of(-1);
        assertEquals("P-1W", negativeOneWeek.toString());
    }
}
