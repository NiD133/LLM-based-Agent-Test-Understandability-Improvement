package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_multipliedBy {

    //-----------------------------------------------------------------------
    public static Object[][] data_valid() {
        return new Object[][] {
                {"PT0H", 0},
                {"PT1H", 1},
                {"PT2H", 2},
                {"PT123456789H", 123456789},
                {"PT+0H", 0},
                {"PT+2H", 2},
                {"PT-0H", 0},
                {"PT-2H", -2},
                {"P0D", 0 * 24},
                {"P1D", 1 * 24},
                {"P2D", 2 * 24},
                {"P1234567D", 1234567 * 24},
                {"P+0D", 0 * 24},
                {"P+2D", 2 * 24},
                {"P-0D", 0 * 24},
                {"P-2D", -2 * 24},
                {"P0DT0H", 0},
                {"P1DT2H", 1 * 24 + 2},
        };
    }

    public static Object[][] data_invalid() {
        return new Object[][] {
                {"P3W"},
                {"P3Q"},
                {"P1M2Y"},
                {"3"},
                {"-3"},
                {"3H"},
                {"-3H"},
                {"P3H"},
                {"P3"},
                {"P-3"},
                {"PH"},
                {"T"},
                {"T3H"},
        };
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_multipliedBy() {
        Hours fiveHours = Hours.of(5);

        assertEquals(Hours.of(0), fiveHours.multipliedBy(0));
        assertEquals(Hours.of(5), fiveHours.multipliedBy(1));
        assertEquals(Hours.of(10), fiveHours.multipliedBy(2));
        assertEquals(Hours.of(15), fiveHours.multipliedBy(3));
        assertEquals(Hours.of(-15), fiveHours.multipliedBy(-3));
    }
}
