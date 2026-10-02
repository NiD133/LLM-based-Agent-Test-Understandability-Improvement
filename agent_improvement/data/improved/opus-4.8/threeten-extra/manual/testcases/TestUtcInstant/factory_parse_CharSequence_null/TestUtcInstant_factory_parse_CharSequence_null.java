package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link UtcInstant#parse} rejects a {@code null} text argument.
 */
public class TestUtcInstant_factory_parse_CharSequence_null {

    @Test
    public void parse_nullText_throwsNullPointerException() {
        // noinspection DataFlowIssue - intentionally passing null to verify the null check
        assertThrows(NullPointerException.class, () -> UtcInstant.parse((String) null));
    }
}
