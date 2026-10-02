package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_adjustInto_null {

    private static final DayOfMonth TEST = DayOfMonth.of(12);

    @Test
    public void test_adjustInto_null() {
        assertThrows(NullPointerException.class, () -> TEST.adjustInto((Temporal) null));
    }
}
