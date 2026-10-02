package org.threeten.extra.chrono;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

@SuppressWarnings("static-method")
public class TestSymmetry010Chronology_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        EqualsTester equalsTester = new EqualsTester();

        equalsTester.addEqualityGroup(
                Symmetry010Date.of(2000, 1, 3),
                Symmetry010Date.of(2000, 1, 3));
        equalsTester.addEqualityGroup(
                Symmetry010Date.of(2000, 1, 4),
                Symmetry010Date.of(2000, 1, 4));
        equalsTester.addEqualityGroup(
                Symmetry010Date.of(2000, 2, 3),
                Symmetry010Date.of(2000, 2, 3));
        equalsTester.addEqualityGroup(
                Symmetry010Date.of(2000, 6, 23),
                Symmetry010Date.of(2000, 6, 23));
        equalsTester.addEqualityGroup(
                Symmetry010Date.of(2000, 6, 28),
                Symmetry010Date.of(2000, 6, 28));
        equalsTester.addEqualityGroup(
                Symmetry010Date.of(2000, 7, 1),
                Symmetry010Date.of(2000, 7, 1));
        equalsTester.addEqualityGroup(
                Symmetry010Date.of(2000, 12, 25),
                Symmetry010Date.of(2000, 12, 25));
        equalsTester.addEqualityGroup(
                Symmetry010Date.of(2000, 12, 28),
                Symmetry010Date.of(2000, 12, 28));
        equalsTester.addEqualityGroup(
                Symmetry010Date.of(2001, 1, 1),
                Symmetry010Date.of(2001, 1, 1));
        equalsTester.addEqualityGroup(
                Symmetry010Date.of(2001, 1, 3),
                Symmetry010Date.of(2001, 1, 3));
        equalsTester.addEqualityGroup(
                Symmetry010Date.of(2001, 12, 28),
                Symmetry010Date.of(2001, 12, 28));
        equalsTester.addEqualityGroup(
                Symmetry010Date.of(2004, 6, 28),
                Symmetry010Date.of(2004, 6, 28));

        equalsTester.testEquals();
    }
}
