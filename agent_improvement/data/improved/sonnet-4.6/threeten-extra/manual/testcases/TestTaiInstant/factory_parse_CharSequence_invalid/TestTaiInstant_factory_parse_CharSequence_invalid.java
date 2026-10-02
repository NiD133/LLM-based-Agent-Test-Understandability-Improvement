package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestTaiInstant_factory_parse_CharSequence_invalid {

    // Valid format is: {digits}.{exactly-9-digits}s(TAI), no leading sign allowed
    static Stream<String> data_badParse() {
        return Stream.of(
            "A.123456789s(TAI)",    // non-numeric seconds part
            "123.12345678As(TAI)",  // non-numeric nanoseconds part
            "123.123456789",        // missing s(TAI) suffix entirely
            "123.123456789s",       // missing (TAI) parenthesized label
            "+123.123456789s(TAI)", // explicit positive sign is not accepted
            "-123.123s(TAI)"        // fewer than 9 nanosecond digits
        );
    }

    @ParameterizedTest
    @MethodSource("data_badParse")
    public void factory_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> TaiInstant.parse(str));
    }
}
