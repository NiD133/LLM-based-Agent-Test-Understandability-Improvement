package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSeconds_test_parse_CharSequence_invalid {

    public static Stream<String> invalidParseInputs() {
        return Stream.of(
                "P3W",
                "P3Q",
                "P1M2Y",
                "3",
                "-3",
                "3S",
                "-3S",
                "P3S",
                "P3",
                "P-3",
                "PS",
                "T3",
                "PT3");
    }

    @ParameterizedTest
    @MethodSource("invalidParseInputs")
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Seconds.parse(str));
    }
}
