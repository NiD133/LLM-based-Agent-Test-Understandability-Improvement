package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestTaiInstant_test_equals_and_hashCode {

    // Three distinct instants, each represented by two separately-constructed
    // instances that must be equal to each other but not to the other groups.
    @Test
    public void test_equals_and_hashCode() {
        TaiInstant groupA1 = TaiInstant.ofTaiSeconds(5L, 20);
        TaiInstant groupA2 = TaiInstant.ofTaiSeconds(5L, 20);

        TaiInstant groupB1 = TaiInstant.ofTaiSeconds(5L, 30);
        TaiInstant groupB2 = TaiInstant.ofTaiSeconds(5L, 30);

        TaiInstant groupC1 = TaiInstant.ofTaiSeconds(6L, 20);
        TaiInstant groupC2 = TaiInstant.ofTaiSeconds(6L, 20);

        new EqualsTester()
                .addEqualityGroup(groupA1, groupA2)   // same second, nano=20
                .addEqualityGroup(groupB1, groupB2)   // same second, nano=30
                .addEqualityGroup(groupC1, groupC2)   // second=6,   nano=20
                .testEquals();
    }
}
