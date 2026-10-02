package org.threeten.extra;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test11 extends MutableClock_ESTest_scaffolding {

    /**
     * Verifies that calling {@code hashCode()} on a clock created via
     * {@link MutableClock#epochUTC()} executes without throwing an exception.
     */
    @Test(timeout = 4000)
    public void hashCode_onEpochUtcClock_doesNotThrow() throws Throwable {
        MutableClock epochUtcClock = MutableClock.epochUTC();

        epochUtcClock.hashCode();
    }
}
