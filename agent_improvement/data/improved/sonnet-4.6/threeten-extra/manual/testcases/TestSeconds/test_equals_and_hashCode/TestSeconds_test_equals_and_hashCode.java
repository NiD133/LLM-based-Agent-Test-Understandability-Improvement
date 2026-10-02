package org.threeten.extra;

import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

public class TestSeconds_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(Seconds.of(5), Seconds.of(5))
                .addEqualityGroup(Seconds.of(6), Seconds.of(6))
                .testEquals();
    }
}
