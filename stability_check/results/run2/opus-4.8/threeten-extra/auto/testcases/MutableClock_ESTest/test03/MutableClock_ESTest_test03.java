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
     * Verifies the equality semantics of {@link MutableClock#withZone}: two views
     * created from the same clock with the same zone share updates and are therefore
     * equal, while a view is not equal to the original clock it came from because
     * the time-zone differs.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        MutableClock utcClock = MutableClock.epochUTC();

        // Create two independent views of the same clock, both using the MIN offset.
        MutableClock minZoneView1 = utcClock.withZone(ZoneOffset.MIN);
        MutableClock minZoneView2 = utcClock.withZone(ZoneOffset.MIN);

        // The two views share the same instant and time-zone, so they are equal.
        assertTrue(minZoneView1.equals(minZoneView2));

        // A view is not equal to the original clock because the time-zone differs
        // (MIN offset vs. UTC), even though they share updates.
        assertFalse(minZoneView2.equals((Object) utcClock));
        assertNotSame(minZoneView2, utcClock);
    }
}
