package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.ZoneOffset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test03 extends MutableClock_ESTest_scaffolding {

    /**
     * Verifies the equality semantics of {@link MutableClock#withZone(java.time.ZoneId)}.
     * <p>
     * Two clocks are equal only when they share the same underlying instant holder
     * <em>and</em> use the same time-zone. Calling {@code withZone} with a zone that
     * differs from the clock's own zone returns a new clock that still shares the
     * original's instant holder, so two such views are equal to each other but not
     * to the original clock (which has a different zone).
     */
    @Test(timeout = 4000)
    public void withZoneViewsAreEqualToEachOtherButNotToOriginal() throws Throwable {
        // Original clock uses the UTC time-zone.
        MutableClock utcClock = MutableClock.epochUTC();

        // Create two independent views of that clock in a different (non-UTC) zone.
        ZoneOffset differentZone = ZoneOffset.MIN;
        MutableClock firstView = utcClock.withZone(differentZone);
        MutableClock secondView = utcClock.withZone(differentZone);

        // The two views share the same instant holder and zone, so they are equal.
        assertTrue(firstView.equals(secondView));

        // A view is not equal to the original clock because their zones differ.
        assertFalse(secondView.equals((Object) utcClock));
        assertNotSame(secondView, utcClock);
    }
}
