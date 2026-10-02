package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#parse(CharSequence)} rejects a {@code null} argument.
 */
public class TestWeeks_test_parse_CharSequence_null {

    @Test
    public void parse_nullText_throwsNullPointerException() {
        //noinspection DataFlowIssue - intentionally passing null to verify the null check
        assertThrows(NullPointerException.class, () -> Weeks.parse((CharSequence) null));
    }
}
