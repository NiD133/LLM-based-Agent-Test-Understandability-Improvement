package org.threeten.extra;

import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Verifies the {@code equals} / {@code hashCode} contract of {@link MutableClock}.
 * <p>
 * Two {@code MutableClock} instances are equal only when they share the same
 * underlying (mutable) instant and use the same time-zone. A clock obtained via
 * {@link MutableClock#withZone} shares the instant of its source clock, so:
 * <ul>
 * <li>changing the zone produces an unequal clock (shared instant, different zone),
 * <li>changing the zone back to the original produces an equal clock again, and
 * <li>a separately constructed clock is never equal, even with the same value.
 * </ul>
 */
public class TestMutableClock_test_equals {

    @Test
    public void test_equals() {
        // Original clock in the UTC zone.
        MutableClock clock = MutableClock.epochUTC();

        // Same shared instant, but a different zone -> not equal to clock.
        MutableClock sharedInstantDifferentZone = clock.withZone(ZoneOffset.MIN);

        // Same shared instant and back to UTC -> equal to clock again.
        MutableClock sharedInstantSameZone = sharedInstantDifferentZone.withZone(ZoneOffset.UTC);

        // An independently created clock; same value but a separate instant -> not equal.
        MutableClock independentClock = MutableClock.epochUTC();

        new EqualsTester()
                .addEqualityGroup(clock, clock, sharedInstantSameZone)
                .addEqualityGroup(sharedInstantDifferentZone)
                .addEqualityGroup(independentClock)
                .testEquals();
    }
}
