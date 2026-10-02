package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Days#parse(CharSequence)} correctly parses every valid
 * ISO-8601 day/week period string into the expected {@code Days} amount.
 * <p>
 * The parser accepts an optional leading sign, the literal {@code P}, an
 * optional weeks section ({@code nW}) and an optional days section ({@code nD}).
 * Each weeks value contributes {@code 7} days. Signs may appear both before the
 * {@code P} and in front of each numeric section.
 */
public class TestDays_test_parse_CharSequence_valid {

    /**
     * Supplies valid period strings paired with the total number of days they
     * represent. The cases are grouped by the parsing feature they exercise.
     */
    static Stream<Arguments> validPeriods() {
        return Stream.of(
                // Plain days section, no sign.
                Arguments.of("P0D", 0),
                Arguments.of("P1D", 1),
                Arguments.of("P2D", 2),
                Arguments.of("P123456789D", 123456789),

                // Days section with an explicit sign on the number.
                Arguments.of("P+0D", 0),
                Arguments.of("P+2D", 2),
                Arguments.of("P-0D", 0),
                Arguments.of("P-2D", -2),

                // Plain weeks section (each week is 7 days), no sign.
                Arguments.of("P0W", 0),
                Arguments.of("P1W", 7),
                Arguments.of("P2W", 14),
                Arguments.of("P12345678W", 12345678 * 7),

                // Weeks section with an explicit sign on the number.
                Arguments.of("P+0W", 0),
                Arguments.of("P+2W", 14),
                Arguments.of("P-0W", 0),
                Arguments.of("P-2W", -14),

                // Combined weeks and days, with signs on either section.
                Arguments.of("P0W0D", 0),
                Arguments.of("P2W3D", 17),
                Arguments.of("P+2W3D", 17),
                Arguments.of("P2W+3D", 17),
                Arguments.of("P-2W3D", -11),
                Arguments.of("P2W-3D", 11),
                Arguments.of("P-2W-3D", -17));
    }

    @ParameterizedTest
    @MethodSource("validPeriods")
    public void parse_returnsExpectedDays(String text, int expectedDays) {
        assertEquals(Days.of(expectedDays), Days.parse(text));
    }
}
