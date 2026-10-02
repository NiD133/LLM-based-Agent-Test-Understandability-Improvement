package org.threeten.extra;

import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

public class TestDays_test_equals_and_hashCode {

    // EqualsTester verifies the equals/hashCode contract: objects in the same
    // equality group must be equal to each other, and unequal to objects in any
    // other group. It also checks that hashCode is consistent with equals.
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(Days.of(5), Days.of(5))
                .addEqualityGroup(Days.of(6), Days.of(6))
                .testEquals();
    }
}
