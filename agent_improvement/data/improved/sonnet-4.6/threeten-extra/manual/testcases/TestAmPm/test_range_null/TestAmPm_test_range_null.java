package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestAmPm_test_range_null {

    @Test
    @DisplayName("range(null) throws NullPointerException")
    public void test_range_null() {
        assertThrows(NullPointerException.class, () -> AmPm.AM.range(null));
    }
}
