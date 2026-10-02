package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestMonths_test_parse_CharSequence_valid_initialMinus {

    public static Object[][] data_valid() {
        return new Object[][] {
                {"P0M", 0},
                {"P1M", 1},
                {"P2M", 2},
                {"P123456789M", 123456789},
                {"P+0M", 0},
                {"P+2M", 2},
                {"P-0M", 0},
                {"P-2M", -2},
                {"P0Y", 0},
                {"P1Y", 12},
                {"P2Y", 24},
                {"P1234567Y", 1234567 * 12},
                {"P+0Y", 0},
                {"P+2Y", 24},
                {"P-0Y", 0},
                {"P-2Y", -24},
                {"P0Y0M", 0},
                {"P2Y3M", 27},
                {"P+2Y3M", 27},
                {"P2Y+3M", 27},
                {"P-2Y3M", -21},
                {"P2Y-3M", 21},
                {"P-2Y-3M", -27}
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid_initialMinus(String periodText, int expectedMonths) {
        assertEquals(Months.of(-expectedMonths), Months.parse("-" + periodText));
    }
}
