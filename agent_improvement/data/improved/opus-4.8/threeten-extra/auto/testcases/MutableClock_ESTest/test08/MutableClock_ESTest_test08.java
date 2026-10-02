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
     * Verifies that two clocks sharing the same instant but using different
     * time-zones are not considered equal. {@code withZone} returns a view that
     * shares updates with the original clock, yet {@code equals} also requires
     * matching time-zones, so a re-zoned view differs from its source.
     */
    @Test(timeout = 4000)
    public void reZonedClockIsNotEqualToOriginalClock() throws Throwable {
        MutableClock utcClock = MutableClock.epochUTC();

        MutableClock reZonedClock = utcClock.withZone(ZoneOffset.MIN);

        boolean clocksAreEqual = utcClock.equals(reZonedClock);
        assertFalse(clocksAreEqual);
    }
}
