package org.threeten.extra.chrono;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestBritishCutoverChronology_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(
                        BritishCutoverDate.of(2000, 1, 3),
                        BritishCutoverDate.of(2000, 1, 3))
                .addEqualityGroup(
                        BritishCutoverDate.of(2000, 1, 4),
                        BritishCutoverDate.of(2000, 1, 4))
                .addEqualityGroup(
                        BritishCutoverDate.of(2000, 2, 3),
                        BritishCutoverDate.of(2000, 2, 3))
                .addEqualityGroup(
                        BritishCutoverDate.of(2001, 1, 3),
                        BritishCutoverDate.of(2001, 1, 3))
                .testEquals();
    }
}
