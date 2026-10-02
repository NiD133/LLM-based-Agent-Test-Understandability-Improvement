package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Verifies that {@link Years#parse(CharSequence)} rejects strings that do not
 * conform to the ISO-8601 year-period format {@code [±]PnY}.
 *
 * <p>Invalid categories covered:
 * <ul>
 *   <li>Wrong ISO-8601 unit suffix (months "M", weeks "W", days "D")</li>
 *   <li>Missing the leading "P" designator</li>
 *   <li>Incomplete format (trailing "Y" absent, or only "PY" with no digit)</li>
 * </ul>
 */
public class TestYears_test_parse_CharSequence_invalid {

    @ParameterizedTest(name = "parse(\"{0}\") throws DateTimeParseException")
    @ValueSource(strings = {
        // Wrong unit suffix — months, weeks, and days are not years
        "P3M", "P3W", "P3D",
        // Missing the "P" period designator
        "3", "-3", "3Y", "-3Y",
        // Incomplete format — no digit after "P", or no unit suffix
        "P3", "P-3", "PY"
    })
    public void test_parse_CharSequence_invalid(String invalidText) {
        assertThrows(DateTimeParseException.class, () -> Years.parse(invalidText));
    }
}
