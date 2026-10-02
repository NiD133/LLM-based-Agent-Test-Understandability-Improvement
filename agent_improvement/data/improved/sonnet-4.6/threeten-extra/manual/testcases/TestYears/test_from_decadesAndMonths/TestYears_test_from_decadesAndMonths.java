package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_from_decadesAndMonths {

    @Test
    public void test_from_decadesAndMonths() {
        // 2 decades = 20 years; -12 months = -1 year; net result = 19 years
        assertEquals(Years.of(19), Years.from(new MockDecadesMonths(2, -12)));
    }
}
