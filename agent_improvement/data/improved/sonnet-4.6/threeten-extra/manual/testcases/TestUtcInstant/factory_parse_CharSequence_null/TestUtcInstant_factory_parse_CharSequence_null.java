package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link UtcInstant#parse(CharSequence)} rejects a null argument
 * with a {@link NullPointerException}.
 */
public class TestUtcInstant_factory_parse_CharSequence_null {

    @Test
    public void factory_parse_CharSequence_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> UtcInstant.parse((String) null));
    }
}
