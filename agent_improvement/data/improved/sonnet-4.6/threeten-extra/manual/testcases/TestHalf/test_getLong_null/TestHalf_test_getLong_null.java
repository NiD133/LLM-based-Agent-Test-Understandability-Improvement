package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHalf_test_getLong_null {

    @Test
    public void test_getLong_null() {
        // getLong(null) must throw NullPointerException per the TemporalAccessor contract
        assertThrows(NullPointerException.class, () -> Half.H2.getLong(null));
    }
}
