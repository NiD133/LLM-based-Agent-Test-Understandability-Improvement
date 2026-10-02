package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_compareTo_nullDayOfMonth {

    @Test
    public void test_compareTo_nullDayOfMonth() {
        DayOfMonth firstDayOfMonth = DayOfMonth.of(1);
        DayOfMonth nullDayOfMonth = null;

        //noinspection DataFlowIssue - explicitly verifies compareTo rejects null.
        assertThrows(NullPointerException.class, () -> firstDayOfMonth.compareTo(nullDayOfMonth));
    }
}
