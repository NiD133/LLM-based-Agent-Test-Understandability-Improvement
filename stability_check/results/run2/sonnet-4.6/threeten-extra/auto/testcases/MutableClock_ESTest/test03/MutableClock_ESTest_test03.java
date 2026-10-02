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
     * Verifies MutableClock equality semantics based on shared InstantHolder and zone.
     *
     * Two clocks are equal when they share the same underlying InstantHolder AND the same zone.
     * withZone() creates a new clock that shares the InstantHolder of the original but has a
     * different zone, so it is not equal to the original clock.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Base clock at epoch with UTC zone
        MutableClock utcClock = MutableClock.epochUTC();

        // Create two independent views with ZoneOffset.MIN (-18:00), both sharing utcClock's InstantHolder
        MutableClock minZoneClock1 = utcClock.withZone(ZoneOffset.MIN);
        MutableClock minZoneClock2 = utcClock.withZone(ZoneOffset.MIN);

        // Clocks with the same shared InstantHolder and the same zone are equal
        assertTrue(minZoneClock1.equals(minZoneClock2));

        // A clock with ZoneOffset.MIN is not equal to the original UTC clock (different zones)
        assertFalse(minZoneClock2.equals((Object) utcClock));

        // withZone() always returns a new instance when the zone differs from the original
        assertNotSame(minZoneClock2, utcClock);
    }
}
