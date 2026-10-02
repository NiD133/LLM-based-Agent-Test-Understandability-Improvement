package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestWeeks_test_multipliedBy {

    @ParameterizedTest(name = "5 weeks multiplied by {0} equals {1} weeks")
    @CsvSource({
        " 0,   0",
        " 1,   5",
        " 2,  10",
        " 3,  15",
        "-3, -15"
    })
    public void test_multipliedBy(int scalar, int expectedWeeks) {
        Weeks test5 = Weeks.of(5);
        assertEquals(Weeks.of(expectedWeeks), test5.multipliedBy(scalar));
    }
}
