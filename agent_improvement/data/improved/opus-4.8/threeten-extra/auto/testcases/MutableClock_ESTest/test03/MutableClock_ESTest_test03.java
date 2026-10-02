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
     * Two views created from the same clock via {@code withZone} share the
     * underlying instant and, when given the same zone, are equal to each
     * other but not to the original clock (which has a different zone).
     */
    @Test(timeout = 4000)
    public void withZoneViewsWithSameZoneAreEqualButDifferFromOriginal() throws Throwable {
        MutableClock utcClock = MutableClock.epochUTC();

        // Re-zone the same clock twice to the same (non-UTC) zone.
        MutableClock minZoneView1 = utcClock.withZone(ZoneOffset.MIN);
        MutableClock minZoneView2 = utcClock.withZone(ZoneOffset.MIN);

        // Both views share the original's instant holder and use the same zone,
        // so they are equal to one another.
        assertTrue(minZoneView1.equals(minZoneView2));

        // The original clock uses UTC, so a re-zoned view is not equal to it.
        assertFalse(minZoneView2.equals((Object) utcClock));
        assertNotSame(minZoneView2, utcClock);
    }
}
