package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.ZoneOffset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test07 extends MutableClock_ESTest_scaffolding {

    /**
     * Verifies that calling withZone with the clock's existing time-zone
     * returns the same clock instance rather than creating a new one.
     *
     * epochUTC() creates a clock already using the UTC zone, so requesting
     * UTC again should be a no-op that returns the original object.
     */
    @Test(timeout = 4000)
    public void withZone_sameZone_returnsSameInstance() throws Throwable {
        MutableClock utcClock = MutableClock.epochUTC();

        MutableClock reZonedClock = utcClock.withZone(ZoneOffset.UTC);

        assertSame(utcClock, reZonedClock);
    }
}
