package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_TemporalAmount_null {

    @Test
    public void test_plus_TemporalAmount_null() {
        Years anyYears = Years.of(5);
        TemporalAmount nullAmount = null;
        assertThrows(NullPointerException.class, () -> anyYears.plus(nullAmount));
    }
}
