package org.threeten.extra.chrono;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Tests that {@link BritishCutoverDate} honours the equals/hashCode contract:
 * two dates are equal only when their year, month and day all match.
 */
public class TestBritishCutoverChronology_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        // Each equality group holds two dates that must be equal to each other
        // but unequal to the dates in every other group. The groups differ by
        // day, month and year respectively, so EqualsTester checks that each
        // component participates in equality and hashCode.
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
