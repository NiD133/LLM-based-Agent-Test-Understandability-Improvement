package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestWeeks_test_parse_CharSequence {

    // Covers zero, positive, large, and sign-combination variants of ISO-8601 week strings.
    // Sign rules: a leading '-' outside 'P' negates the whole value;
    // a '-' inside (e.g. "P-2W") sets the week count negative;
    // combining both double-negates back to positive.
    @ParameterizedTest(name = "parse(\"{0}\") == Weeks.of({1})")
    @CsvSource({
        // zero
        "P0W,          0",
        // plain positive values
        "P1W,          1",
        "P2W,          2",
        // large positive value
        "P123456789W,  123456789",
        // negative week count inside the string ("P-nW")
        "P-2W,        -2",
        // leading minus sign negates a positive week count ("-PnW")
        "-P2W,        -2",
        // leading minus + negative week count → double negation yields positive ("-P-nW")
        "-P-2W,        2"
    })
    public void test_parse_CharSequence(String text, int expectedWeeks) {
        assertEquals(Weeks.of(expectedWeeks), Weeks.parse(text));
    }
}
