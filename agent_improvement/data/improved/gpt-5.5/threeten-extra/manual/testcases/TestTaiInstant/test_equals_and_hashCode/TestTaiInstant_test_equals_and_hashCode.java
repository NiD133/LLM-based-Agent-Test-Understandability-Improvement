package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestTaiInstant_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        TaiInstant fiveSecondsTwentyNanos = TaiInstant.ofTaiSeconds(5L, 20);
        TaiInstant anotherFiveSecondsTwentyNanos = TaiInstant.ofTaiSeconds(5L, 20);
        TaiInstant fiveSecondsThirtyNanos = TaiInstant.ofTaiSeconds(5L, 30);
        TaiInstant anotherFiveSecondsThirtyNanos = TaiInstant.ofTaiSeconds(5L, 30);
        TaiInstant sixSecondsTwentyNanos = TaiInstant.ofTaiSeconds(6L, 20);
        TaiInstant anotherSixSecondsTwentyNanos = TaiInstant.ofTaiSeconds(6L, 20);

        new EqualsTester()
                .addEqualityGroup(fiveSecondsTwentyNanos, anotherFiveSecondsTwentyNanos)
                .addEqualityGroup(fiveSecondsThirtyNanos, anotherFiveSecondsThirtyNanos)
                .addEqualityGroup(sixSecondsTwentyNanos, anotherSixSecondsTwentyNanos)
                .testEquals();
    }
}
