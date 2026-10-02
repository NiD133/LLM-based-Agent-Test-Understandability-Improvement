package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestYears_test_from_null {

    @Test
    @DisplayName("Years.from(null) throws NullPointerException")
    public void test_from_null() {
        assertThrows(NullPointerException.class, () -> Years.from((TemporalAmount) null));
    }
}
