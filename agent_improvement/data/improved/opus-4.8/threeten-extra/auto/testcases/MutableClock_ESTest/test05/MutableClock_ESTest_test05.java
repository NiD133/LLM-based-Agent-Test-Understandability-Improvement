package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test05 extends MutableClock_ESTest_scaffolding {

    /**
     * A MutableClock is never equal to an object that is not a MutableClock.
     */
    @Test(timeout = 4000)
    public void equals_returnsFalse_whenComparedToNonClockObject() throws Throwable {
        MutableClock clock = MutableClock.epochUTC();
        Object unrelatedObject = new Object();

        boolean isEqual = clock.equals(unrelatedObject);

        assertFalse(isEqual);
    }
}
