package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_parse_CharSequence_null {

    @Test
    public void factory_parse_CharSequence_null() {
        //noinspection DataFlowIssue - deliberately verifies the null contract
        assertThrows(NullPointerException.class, () -> UtcInstant.parse((String) null));
    }
}
