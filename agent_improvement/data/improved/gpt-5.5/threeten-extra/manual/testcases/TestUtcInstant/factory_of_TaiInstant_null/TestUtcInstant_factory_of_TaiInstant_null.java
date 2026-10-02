package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_of_TaiInstant_null {

    @Test
    public void factory_of_TaiInstant_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> UtcInstant.of((TaiInstant) null));
    }
}
