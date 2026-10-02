package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_from_TemporalAccessor_null {

    @Test
    public void test_from_TemporalAccessor_null() {
        // AmPm.from must reject a null argument with NullPointerException
        assertThrows(NullPointerException.class, () -> AmPm.from((TemporalAccessor) null));
    }
}
