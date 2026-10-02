package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_getLong_null {

    @Test
    public void test_getLong_null() {
        assertThrows(NullPointerException.class, () -> AmPm.PM.getLong(null));
    }
}
