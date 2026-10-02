package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests that {@link Seconds#parse(CharSequence)} rejects malformed text.
 */
public class TestSeconds_test_parse_CharSequence_invalid {

    /**
     * Each string violates the ISO-8601 {@code PnDTnHnMnS} grammar that
     * {@code Seconds.parse} accepts, so parsing must fail. The cases cover:
     * <ul>
     *   <li>unsupported units: "P3W", "P3Q", "P1M2Y"</li>
     *   <li>missing the mandatory "P" prefix: "3", "-3", "3S", "-3S"</li>
     *   <li>a seconds section without the required "T": "P3S"</li>
     *   <li>a number without any unit suffix: "P3", "P-3"</li>
     *   <li>a unit suffix without any number: "PS", "PT3" (and "T3" lacking "P")</li>
     * </ul>
     */
    @ParameterizedTest
    @ValueSource(strings = {
        "P3W", "P3Q", "P1M2Y", "3", "-3", "3S", "-3S", "P3S", "P3", "P-3", "PS", "T3", "PT3"
    })
    public void parse_rejectsInvalidText(String invalidText) {
        assertThrows(DateTimeParseException.class, () -> Seconds.parse(invalidText));
    }
}
