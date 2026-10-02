package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
            // baseline: year=2000, month=1, day=3
            .addEqualityGroup(Symmetry454Date.of(2000, 1, 3), Symmetry454Date.of(2000, 1, 3))
            // different day within same month
            .addEqualityGroup(Symmetry454Date.of(2000, 1, 4), Symmetry454Date.of(2000, 1, 4))
            // different month, same year and day
            .addEqualityGroup(Symmetry454Date.of(2000, 2, 3), Symmetry454Date.of(2000, 2, 3))
            // different year, same month and day
            .addEqualityGroup(Symmetry454Date.of(2001, 1, 3), Symmetry454Date.of(2001, 1, 3))
            // last day of December in year 2000 (non-leap: 28 days)
            .addEqualityGroup(Symmetry454Date.of(2000, 12, 28), Symmetry454Date.of(2000, 12, 28))
            // earlier day in December year 2000
            .addEqualityGroup(Symmetry454Date.of(2000, 12, 25), Symmetry454Date.of(2000, 12, 25))
            // first day of next year
            .addEqualityGroup(Symmetry454Date.of(2001, 1, 1), Symmetry454Date.of(2001, 1, 1))
            // last day of December year 2001 (non-leap: 28 days)
            .addEqualityGroup(Symmetry454Date.of(2001, 12, 28), Symmetry454Date.of(2001, 12, 28))
            // last day of June year 2000 (long month: 35 days, but here day=28)
            .addEqualityGroup(Symmetry454Date.of(2000, 6, 28), Symmetry454Date.of(2000, 6, 28))
            // mid-month June year 2000
            .addEqualityGroup(Symmetry454Date.of(2000, 6, 23), Symmetry454Date.of(2000, 6, 23))
            // first day of July year 2000 (follows June)
            .addEqualityGroup(Symmetry454Date.of(2000, 7, 1), Symmetry454Date.of(2000, 7, 1))
            // leap year 2004: last day of June (long month including leap week)
            .addEqualityGroup(Symmetry454Date.of(2004, 6, 28), Symmetry454Date.of(2004, 6, 28))
            .testEquals();
    }
}
