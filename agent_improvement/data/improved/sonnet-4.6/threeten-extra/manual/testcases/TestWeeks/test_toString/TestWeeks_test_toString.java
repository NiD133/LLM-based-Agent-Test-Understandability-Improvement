package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_toString {

    // Weeks.toString() follows the ISO-8601 duration format "PnW",
    // where n is the signed number of weeks (e.g. "P5W", "P-1W").
    @Test
    public void test_toString() {
        Weeks fiveWeeks = Weeks.of(5);
        assertEquals("P5W", fiveWeeks.toString());

        Weeks minusOneWeek = Weeks.of(-1);
        assertEquals("P-1W", minusOneWeek.toString());
    }
}
