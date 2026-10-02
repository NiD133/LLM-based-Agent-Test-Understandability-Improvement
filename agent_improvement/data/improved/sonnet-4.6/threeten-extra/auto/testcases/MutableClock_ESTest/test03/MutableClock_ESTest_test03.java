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
     * Verifies equals() semantics for MutableClock views:
     * - Two clocks derived from the same parent with the same zone are equal
     *   (they share the same underlying InstantHolder).
     * - A clock with a different zone is not equal, even if the instant is the same.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        MutableClock utcClock = MutableClock.epochUTC();
        ZoneOffset minOffset = ZoneOffset.MIN;

        // Both views share the same InstantHolder and the same zone, so they must be equal.
        MutableClock viewA = utcClock.withZone(minOffset);
        MutableClock viewB = utcClock.withZone(minOffset);
        assertTrue(viewA.equals(viewB));

        // utcClock uses UTC whereas viewB uses ZoneOffset.MIN, so they must not be equal.
        assertFalse(viewB.equals((Object) utcClock));

        // withZone() returns a new instance when the zone differs from the clock's own zone.
        assertNotSame(viewB, utcClock);
    }
}
