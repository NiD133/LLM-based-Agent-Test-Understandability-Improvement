package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_toString {

    @Test
    public void test_toString() {
        Years positiveYears = Years.of(5);
        assertEquals("P5Y", positiveYears.toString());

        Years negativeYears = Years.of(-1);
        assertEquals("P-1Y", negativeYears.toString());
    }
}
