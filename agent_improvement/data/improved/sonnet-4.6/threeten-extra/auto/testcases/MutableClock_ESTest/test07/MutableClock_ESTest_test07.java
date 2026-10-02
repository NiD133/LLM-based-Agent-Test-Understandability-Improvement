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

    // withZone returns the same instance when the requested zone equals the clock's current zone
    @Test(timeout = 4000)
    public void test07_withZone_returnsSameInstance_whenZoneIsUnchanged() throws Throwable {
        MutableClock clockAtEpochUtc = MutableClock.epochUTC();
        MutableClock clockWithSameZone = clockAtEpochUtc.withZone(ZoneOffset.UTC);
        assertSame(clockWithSameZone, clockAtEpochUtc);
    }
}
