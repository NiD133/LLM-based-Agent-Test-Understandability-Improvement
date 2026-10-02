package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_atTime_null {

    @Test
    public void test_atTime_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> BritishCutoverDate.of(2014, 5, 26).atTime(null));
    }
}
