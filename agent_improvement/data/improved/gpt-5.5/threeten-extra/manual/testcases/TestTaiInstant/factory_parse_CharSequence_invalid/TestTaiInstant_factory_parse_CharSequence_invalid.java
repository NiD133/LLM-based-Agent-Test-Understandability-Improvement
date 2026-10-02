package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestTaiInstant_factory_parse_CharSequence_invalid {

    public static Object[][] data_badParse() {
        return new Object[][] {
                { "A.123456789s(TAI)" },
                { "123.12345678As(TAI)" },
                { "123.123456789" },
                { "123.123456789s" },
                { "+123.123456789s(TAI)" },
                { "-123.123s(TAI)" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badParse")
    public void factory_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> TaiInstant.parse(str));
    }
}
