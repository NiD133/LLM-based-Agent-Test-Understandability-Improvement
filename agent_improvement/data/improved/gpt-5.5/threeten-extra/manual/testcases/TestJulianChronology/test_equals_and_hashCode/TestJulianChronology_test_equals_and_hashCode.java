package org.threeten.extra.chrono;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestJulianChronology_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        JulianDate januaryThird2000 = JulianDate.of(2000, 1, 3);
        JulianDate anotherJanuaryThird2000 = JulianDate.of(2000, 1, 3);
        JulianDate januaryFourth2000 = JulianDate.of(2000, 1, 4);
        JulianDate anotherJanuaryFourth2000 = JulianDate.of(2000, 1, 4);
        JulianDate februaryThird2000 = JulianDate.of(2000, 2, 3);
        JulianDate anotherFebruaryThird2000 = JulianDate.of(2000, 2, 3);
        JulianDate januaryThird2001 = JulianDate.of(2001, 1, 3);
        JulianDate anotherJanuaryThird2001 = JulianDate.of(2001, 1, 3);

        new EqualsTester()
                .addEqualityGroup(januaryThird2000, anotherJanuaryThird2000)
                .addEqualityGroup(januaryFourth2000, anotherJanuaryFourth2000)
                .addEqualityGroup(februaryThird2000, anotherFebruaryThird2000)
                .addEqualityGroup(januaryThird2001, anotherJanuaryThird2001)
                .testEquals();
    }
}
