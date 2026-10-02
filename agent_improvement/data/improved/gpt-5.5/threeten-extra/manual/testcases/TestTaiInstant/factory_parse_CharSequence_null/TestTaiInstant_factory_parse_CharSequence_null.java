package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_factory_parse_CharSequence_null {

    @Test
    public void factory_parse_CharSequence_null() {
        //noinspection DataFlowIssue - verifies the public null-input contract
        assertThrows(NullPointerException.class, () -> TaiInstant.parse(null));
    }
}
