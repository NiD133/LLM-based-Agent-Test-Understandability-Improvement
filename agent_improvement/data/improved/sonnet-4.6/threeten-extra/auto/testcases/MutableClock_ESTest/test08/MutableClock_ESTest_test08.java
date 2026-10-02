package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.ZoneOffset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test08 extends MutableClock_ESTest_scaffolding {

    /**
     * Verifies that two MutableClocks sharing the same instant but using
     * different time-zones are not considered equal.
     *
     * MutableClock.equals() requires both shared-update identity AND the same
     * zone, so a clock derived via withZone() with a different zone must not
     * equal the original.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        MutableClock utcClock = MutableClock.epochUTC();

        // withZone() returns a new clock that shares the same instant holder
        // but has a different time-zone (ZoneOffset.MIN == UTC-18:00).
        ZoneOffset minOffset = ZoneOffset.MIN;
        MutableClock differentZoneClock = utcClock.withZone(minOffset);

        // Even though the two clocks share instant updates, they differ in
        // zone, so equals() must return false.
        boolean clocksAreEqual = utcClock.equals(differentZoneClock);
        assertFalse("Clocks with different zones should not be equal even if they share the same instant",
                clocksAreEqual);
    }
}
