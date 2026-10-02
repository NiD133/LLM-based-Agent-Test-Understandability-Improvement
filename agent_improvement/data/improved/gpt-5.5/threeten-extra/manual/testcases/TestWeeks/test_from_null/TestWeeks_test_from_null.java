package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_from_null {

    @Test
    public void fromRejectsNullTemporalAmount() {
        //noinspection DataFlowIssue - verifies the public null-handling contract
        assertThrows(NullPointerException.class, () -> Weeks.from((TemporalAmount) null));
    }
}
