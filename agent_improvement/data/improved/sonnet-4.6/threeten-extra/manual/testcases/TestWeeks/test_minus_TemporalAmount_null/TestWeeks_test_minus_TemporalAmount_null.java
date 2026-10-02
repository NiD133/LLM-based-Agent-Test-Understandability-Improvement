package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_TemporalAmount_null {

    @Test
    public void test_minus_TemporalAmount_null() {
        Weeks weeks = Weeks.of(Integer.MIN_VALUE + 1);
        TemporalAmount nullAmount = null;
        assertThrows(NullPointerException.class, () -> weeks.minus(nullAmount));
    }
}
