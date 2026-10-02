package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test16 extends Minutes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        // 53041 hours * 60 = 3,182,460 minutes
        Minutes minutesFromHours = Minutes.ofHours(53041);
        boolean isZero = minutesFromHours.isZero();

        assertEquals(3182460, minutesFromHours.getAmount());
        assertFalse(isZero);
    }
}
