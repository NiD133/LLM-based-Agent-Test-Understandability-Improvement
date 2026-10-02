package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests that {@link TaiInstant#parse(CharSequence)} rejects malformed text.
 * <p>
 * The accepted format is the strict {@code {seconds}.{nanosOfSecond}s(TAI)} pattern,
 * where the seconds part is digits with an optional leading minus sign, the nanos part
 * is exactly nine digits, and the {@code s(TAI)} suffix is mandatory. Any deviation
 * must raise a {@link DateTimeParseException}.
 */
public class TestTaiInstant_factory_parse_CharSequence_invalid {

    /**
     * Each value is malformed in a distinct way, so parsing it must fail:
     * <ul>
     * <li>{@code "A.123456789s(TAI)"}    - non-numeric seconds part</li>
     * <li>{@code "123.12345678As(TAI)"}  - non-numeric character in the nanos part</li>
     * <li>{@code "123.123456789"}        - missing the {@code s(TAI)} suffix</li>
     * <li>{@code "123.123456789s"}       - missing the {@code (TAI)} part of the suffix</li>
     * <li>{@code "+123.123456789s(TAI)"} - leading plus sign is not allowed</li>
     * <li>{@code "-123.123s(TAI)"}       - nanos part has 3 digits instead of 9</li>
     * </ul>
     */
    @ParameterizedTest
    @ValueSource(strings = {
        "A.123456789s(TAI)",
        "123.12345678As(TAI)",
        "123.123456789",
        "123.123456789s",
        "+123.123456789s(TAI)",
        "-123.123s(TAI)"
    })
    public void factory_parse_CharSequence_invalid(String invalidText) {
        assertThrows(DateTimeParseException.class, () -> TaiInstant.parse(invalidText));
    }
}
