package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

public class TestTaiInstant_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        // Three distinct TaiInstant values: different nanos with same seconds, and different seconds.
        // Each pair within a group must be equal; pairs across groups must not be equal.
        TaiInstant sameSeconds5Nano20a = TaiInstant.ofTaiSeconds(5L, 20);
        TaiInstant sameSeconds5Nano20b = TaiInstant.ofTaiSeconds(5L, 20);

        TaiInstant sameSeconds5Nano30a = TaiInstant.ofTaiSeconds(5L, 30);
        TaiInstant sameSeconds5Nano30b = TaiInstant.ofTaiSeconds(5L, 30);

        TaiInstant sameSeconds6Nano20a = TaiInstant.ofTaiSeconds(6L, 20);
        TaiInstant sameSeconds6Nano20b = TaiInstant.ofTaiSeconds(6L, 20);

        new EqualsTester()
                .addEqualityGroup(sameSeconds5Nano20a, sameSeconds5Nano20b)
                .addEqualityGroup(sameSeconds5Nano30a, sameSeconds5Nano30b)
                .addEqualityGroup(sameSeconds6Nano20a, sameSeconds6Nano20b)
                .testEquals();
    }
}
