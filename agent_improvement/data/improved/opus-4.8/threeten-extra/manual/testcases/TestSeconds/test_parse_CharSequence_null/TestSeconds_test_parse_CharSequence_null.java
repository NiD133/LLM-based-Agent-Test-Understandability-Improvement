package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Seconds#parse(CharSequence)} rejects a null argument.
 */
public class TestSeconds_test_parse_CharSequence_null {

    @Test
    public void parse_nullCharSequence_throwsNullPointerException() {
        //noinspection DataFlowIssue - intentionally passing null to verify the null-check
        assertThrows(NullPointerException.class, () -> Seconds.parse((CharSequence) null));
    }
}
