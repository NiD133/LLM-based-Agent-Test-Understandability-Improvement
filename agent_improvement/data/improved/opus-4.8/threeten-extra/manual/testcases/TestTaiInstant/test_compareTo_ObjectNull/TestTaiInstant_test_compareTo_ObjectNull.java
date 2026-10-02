package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link TaiInstant#compareTo(TaiInstant)} rejects a {@code null} argument.
 */
public class TestTaiInstant_test_compareTo_ObjectNull {

    @Test
    public void compareTo_nullArgument_throwsNullPointerException() {
        TaiInstant instant = TaiInstant.ofTaiSeconds(0L, 0);

        //noinspection DataFlowIssue - intentionally passing null to verify the contract
        assertThrows(NullPointerException.class, () -> instant.compareTo(null));
    }
}
