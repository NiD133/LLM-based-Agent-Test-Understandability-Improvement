package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_compareTo_ObjectNull {

    @Test
    public void test_compareTo_ObjectNull() {
        UtcInstant a = UtcInstant.ofModifiedJulianDay(0L, 0);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> a.compareTo(null));
    }
}
