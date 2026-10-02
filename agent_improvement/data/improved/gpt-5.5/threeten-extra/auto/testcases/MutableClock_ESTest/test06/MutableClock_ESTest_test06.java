package org.threeten.extra;

import static org.junit.Assert.*;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test06 extends MutableClock_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        MutableClock epochUtcClock = MutableClock.epochUTC();

        boolean clockEqualsItself = epochUtcClock.equals(epochUtcClock);

        assertTrue(clockEqualsItself);
    }
}
