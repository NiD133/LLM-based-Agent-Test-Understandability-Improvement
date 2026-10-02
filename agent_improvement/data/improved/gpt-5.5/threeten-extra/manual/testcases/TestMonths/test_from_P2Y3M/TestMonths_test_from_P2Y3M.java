package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_from_P2Y3M {

    @Test
    public void test_from_P2Y3M() {
        Months result = Months.from(new MockYearsMonths(2, 3));

        assertEquals(Months.of(27), result);
    }
}
