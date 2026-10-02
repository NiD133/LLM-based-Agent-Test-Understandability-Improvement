package org.threeten.extra;

import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestMutableClock_test_equals {

    @Test
    public void test_equals() {
        MutableClock baseClock = MutableClock.epochUTC();
        MutableClock sameInstantDifferentZone = baseClock.withZone(ZoneOffset.MIN);
        MutableClock sameInstantSameZone = sameInstantDifferentZone.withZone(ZoneOffset.UTC);
        MutableClock independentClock = MutableClock.epochUTC();

        new EqualsTester()
                .addEqualityGroup(baseClock, baseClock, sameInstantSameZone)
                .addEqualityGroup(sameInstantDifferentZone)
                .addEqualityGroup(independentClock)
                .testEquals();
    }
}
