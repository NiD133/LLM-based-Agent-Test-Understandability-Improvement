package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_adjustInto_null {

    private static final DayOfYear TEST_DAY = DayOfYear.of(12);

    @Test
    public void test_adjustInto_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> TEST_DAY.adjustInto((Temporal) null));
    }
}
