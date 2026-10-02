package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_factory_of_UtcInstant_null {

    @Test
    public void factory_of_UtcInstant_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> TaiInstant.of((UtcInstant) null));
    }
}
