package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_toString {

    @Test
    public void test_toString() {
        // toString() must produce ISO-8601 duration format "PTnM"
        Minutes fiveMinutes = Minutes.of(5);
        assertEquals("PT5M", fiveMinutes.toString());

        Minutes negativeOneMinute = Minutes.of(-1);
        assertEquals("PT-1M", negativeOneMinute.toString());
    }
}
