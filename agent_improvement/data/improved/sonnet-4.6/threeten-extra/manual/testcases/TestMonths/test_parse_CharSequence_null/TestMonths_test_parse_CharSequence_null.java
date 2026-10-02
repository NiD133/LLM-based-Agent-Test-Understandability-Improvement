package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_parse_CharSequence_null {

    /**
     * Verifies that {@link Months#parse(CharSequence)} rejects a null argument
     * with a {@link NullPointerException}, as required by its contract.
     */
    @Test
    public void test_parse_CharSequence_null() {
        assertThrows(NullPointerException.class, () -> Months.parse((CharSequence) null));
    }
}
