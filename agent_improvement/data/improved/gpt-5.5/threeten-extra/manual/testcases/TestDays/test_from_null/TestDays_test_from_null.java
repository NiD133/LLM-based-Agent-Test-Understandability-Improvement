package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

public class TestDays_test_from_null {

    @Test
    public void test_from_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Days.from((TemporalAmount) null));
    }
}
