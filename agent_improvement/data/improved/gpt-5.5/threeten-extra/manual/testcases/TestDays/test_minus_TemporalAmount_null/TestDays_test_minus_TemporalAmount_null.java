package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

public class TestDays_test_minus_TemporalAmount_null {

    @Test
    public void test_minus_TemporalAmount_null() {
        Days days = Days.of(Integer.MIN_VALUE + 1);
        TemporalAmount amountToSubtract = null;

        assertThrows(NullPointerException.class, () -> days.minus(amountToSubtract));
    }
}
