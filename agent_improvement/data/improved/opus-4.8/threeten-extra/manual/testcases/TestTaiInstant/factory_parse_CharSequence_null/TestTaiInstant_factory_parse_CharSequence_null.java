package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link TaiInstant#parse(CharSequence)} rejects a {@code null} argument.
 */
public class TestTaiInstant_factory_parse_CharSequence_null {

    @Test
    public void parse_null_throwsNullPointerException() {
        //noinspection DataFlowIssue - intentionally passing null to verify the null check
        assertThrows(NullPointerException.class, () -> TaiInstant.parse(null));
    }
}
