package org.threeten.extra;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestWeeks_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        Weeks fiveWeeks = Weeks.of(5);
        Weeks anotherFiveWeeks = Weeks.of(5);
        Weeks sixWeeks = Weeks.of(6);
        Weeks anotherSixWeeks = Weeks.of(6);

        new EqualsTester()
                .addEqualityGroup(fiveWeeks, anotherFiveWeeks)
                .addEqualityGroup(sixWeeks, anotherSixWeeks)
                .testEquals();
    }
}
