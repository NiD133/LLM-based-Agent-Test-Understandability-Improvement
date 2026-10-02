package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestJulianChronology_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(JulianDate.of(2000, 1, 3), JulianDate.of(2000, 1, 3))
                .addEqualityGroup(JulianDate.of(2000, 1, 4), JulianDate.of(2000, 1, 4))
                .addEqualityGroup(JulianDate.of(2000, 2, 3), JulianDate.of(2000, 2, 3))
                .addEqualityGroup(JulianDate.of(2001, 1, 3), JulianDate.of(2001, 1, 3))
                .testEquals();
    }
}
