package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestWeeks_test_compareTo {

    @Test
    public void test_compareTo() {
        Weeks fewerWeeks = Weeks.of(5);
        Weeks moreWeeks = Weeks.of(6);

        // equal: comparing to itself returns 0
        assertEquals(0, fewerWeeks.compareTo(fewerWeeks));

        // less-than: smaller value compared to larger returns negative
        assertEquals(-1, fewerWeeks.compareTo(moreWeeks));

        // greater-than: larger value compared to smaller returns positive
        assertEquals(1, moreWeeks.compareTo(fewerWeeks));
    }
}
