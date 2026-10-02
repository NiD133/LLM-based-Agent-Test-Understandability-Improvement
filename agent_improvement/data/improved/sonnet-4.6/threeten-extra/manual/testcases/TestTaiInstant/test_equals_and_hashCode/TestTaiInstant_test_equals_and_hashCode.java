package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestTaiInstant_test_equals_and_hashCode {

    // Three distinct TaiInstant values used to define separate equality groups:
    //   Group A: seconds=5, nano=20
    //   Group B: seconds=5, nano=30  (same second, different nano)
    //   Group C: seconds=6, nano=20  (different second, same nano)
    @Test
    public void test_equals_and_hashCode() {
        TaiInstant groupA1 = TaiInstant.ofTaiSeconds(5L, 20);
        TaiInstant groupA2 = TaiInstant.ofTaiSeconds(5L, 20);

        TaiInstant groupB1 = TaiInstant.ofTaiSeconds(5L, 30);
        TaiInstant groupB2 = TaiInstant.ofTaiSeconds(5L, 30);

        TaiInstant groupC1 = TaiInstant.ofTaiSeconds(6L, 20);
        TaiInstant groupC2 = TaiInstant.ofTaiSeconds(6L, 20);

        new EqualsTester()
                .addEqualityGroup(groupA1, groupA2)
                .addEqualityGroup(groupB1, groupB2)
                .addEqualityGroup(groupC1, groupC2)
                .testEquals();
    }
}
