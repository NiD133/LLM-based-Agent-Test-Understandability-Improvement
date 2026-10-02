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
     * Verifies MutableClock equality rules:
     * - Two clocks sharing the same instant-holder AND the same zone are equal.
     * - A clock whose zone differs from the original is not equal, even though
     *   the two share the same underlying instant-holder.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Base clock at UTC epoch; withZone() returns a new clock that shares
        // the same InstantHolder but carries the requested zone.
        MutableClock utcBaseClock = MutableClock.epochUTC();
        ZoneOffset minZone = ZoneOffset.MIN;

        // Both derived clocks share the same InstantHolder from utcBaseClock
        // and have identical zones, so they must be equal.
        MutableClock minZoneClock1 = utcBaseClock.withZone(minZone);
        MutableClock minZoneClock2 = utcBaseClock.withZone(minZone);

        assertTrue("Clocks sharing the same instant-holder and zone should be equal",
                minZoneClock1.equals(minZoneClock2));

        // minZoneClock2 shares the instant-holder with utcBaseClock but has a
        // different zone (MIN vs UTC), so the two must NOT be equal.
        assertFalse("Clocks with different zones should not be equal even when sharing an instant-holder",
                minZoneClock2.equals((Object) utcBaseClock));

        // Confirm minZoneClock2 and utcBaseClock are distinct object instances.
        assertNotSame(minZoneClock2, utcBaseClock);
    }
}
