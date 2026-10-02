package org.threeten.extra.chrono;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_equals_and_hashCode {

    private static final int[][] EQUAL_DATE_GROUPS = {
            { 2000, 1, 3 },
            { 2000, 1, 4 },
            { 2000, 2, 3 },
            { 2001, 1, 3 },
            { 2000, 12, 28 },
            { 2000, 12, 25 },
            { 2001, 1, 1 },
            { 2001, 12, 28 },
            { 2000, 6, 28 },
            { 2000, 6, 23 },
            { 2000, 7, 1 },
            { 2004, 6, 28 },
    };

    @Test
    public void test_equals_and_hashCode() {
        EqualsTester equalsTester = new EqualsTester();

        for (int[] dateGroup : EQUAL_DATE_GROUPS) {
            addEqualDatePair(equalsTester, dateGroup[0], dateGroup[1], dateGroup[2]);
        }

        equalsTester.testEquals();
    }

    private static void addEqualDatePair(EqualsTester equalsTester, int year, int month, int dayOfMonth) {
        equalsTester.addEqualityGroup(
                Symmetry454Date.of(year, month, dayOfMonth),
                Symmetry454Date.of(year, month, dayOfMonth));
    }
}
