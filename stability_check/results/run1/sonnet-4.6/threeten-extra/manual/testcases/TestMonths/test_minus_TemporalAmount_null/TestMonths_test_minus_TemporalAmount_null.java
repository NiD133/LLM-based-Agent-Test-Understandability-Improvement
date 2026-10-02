package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

public class TestMonths_test_minus_TemporalAmount_null {

    @Test
    public void test_minus_TemporalAmount_null() {
        Months months = Months.of(Integer.MIN_VALUE + 1);
        TemporalAmount nullAmount = null;

        //noinspection DataFlowIssue - verifies that passing null throws NullPointerException
        assertThrows(NullPointerException.class, () -> months.minus(nullAmount));
    }
}
