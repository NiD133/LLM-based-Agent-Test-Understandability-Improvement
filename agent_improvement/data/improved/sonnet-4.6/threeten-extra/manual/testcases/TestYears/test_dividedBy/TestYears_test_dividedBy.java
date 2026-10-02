package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestYears_test_dividedBy {

    // Integer division: 12 / divisor, truncating toward zero
    @ParameterizedTest(name = "Years.of(12).dividedBy({0}) == Years.of({1})")
    @CsvSource({
        " 1,  12",
        " 2,   6",
        " 3,   4",
        " 4,   3",
        " 5,   2",
        " 6,   2",
        "-3,  -4"
    })
    public void test_dividedBy(int divisor, int expectedYears) {
        Years twelveYears = Years.of(12);
        assertEquals(Years.of(expectedYears), twelveYears.dividedBy(divisor));
    }
}
