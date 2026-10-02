package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestMutableClock_test_equals {

    /**
     * MutableClock equality requires both:
     *   1. Shared updates (same internal InstantHolder instance), AND
     *   2. The same time-zone.
     *
     * Calling withZone() on a clock creates a view that shares the same
     * InstantHolder, so both clocks advance together. Two such views are equal
     * when they also share the same zone. An independently created clock always
     * gets its own InstantHolder, so it is never equal to another clock even if
     * it happens to report the same instant and zone.
     */
    @Test
    public void test_equals() {
        // Base clock: UTC epoch, owns its own InstantHolder.
        MutableClock clock = MutableClock.epochUTC();

        // A view of the same instant-holder but with a different zone.
        // Not equal to `clock` because the zones differ.
        MutableClock withOtherZone = clock.withZone(ZoneOffset.MIN);

        // Switching back to UTC re-establishes zone equality while still sharing
        // the same InstantHolder — so this view IS equal to `clock`.
        MutableClock withSameZone = withOtherZone.withZone(ZoneOffset.UTC);

        // A brand-new clock: same zone and instant as `clock`, but a completely
        // independent InstantHolder — therefore NOT equal to `clock`.
        MutableClock independent = MutableClock.epochUTC();

        new EqualsTester()
                // clock and withSameZone share the same InstantHolder and zone → equal
                .addEqualityGroup(clock, clock, withSameZone)
                // withOtherZone shares the InstantHolder but has a different zone → separate group
                .addEqualityGroup(withOtherZone)
                // independent has its own InstantHolder → separate group
                .addEqualityGroup(independent)
                .testEquals();
    }
}
