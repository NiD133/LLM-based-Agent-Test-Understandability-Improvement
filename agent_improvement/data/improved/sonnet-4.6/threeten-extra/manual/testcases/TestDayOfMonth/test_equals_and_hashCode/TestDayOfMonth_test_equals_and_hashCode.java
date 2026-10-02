package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestDayOfMonth_test_equals_and_hashCode {

    private static final int MAX_LENGTH = 31;

    @Test
    public void test_equals_and_hashCode() {
        EqualsTester equalsTester = new EqualsTester();
        for (int i = 1; i <= MAX_LENGTH; i++) {
            equalsTester.addEqualityGroup(DayOfMonth.of(i), DayOfMonth.of(i));
        }
        equalsTester.testEquals();
    }
}
