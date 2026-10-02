package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test24 extends Minutes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testOfHoursWithZeroHoursReturnsZeroMinutes() throws Throwable {
        Minutes zeroHours = Minutes.ofHours(0);
        assertEquals(0, zeroHours.getAmount());
    }
}
