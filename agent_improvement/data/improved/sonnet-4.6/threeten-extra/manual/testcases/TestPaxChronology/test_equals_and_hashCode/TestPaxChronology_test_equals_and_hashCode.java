package org.threeten.extra.chrono;

import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(PaxDate.of(2000, 1, 3), PaxDate.of(2000, 1, 3))
                .addEqualityGroup(PaxDate.of(2000, 1, 4), PaxDate.of(2000, 1, 4))
                .addEqualityGroup(PaxDate.of(2000, 2, 3), PaxDate.of(2000, 2, 3))
                .addEqualityGroup(PaxDate.of(2001, 1, 3), PaxDate.of(2001, 1, 3))
                .testEquals();
    }
}
