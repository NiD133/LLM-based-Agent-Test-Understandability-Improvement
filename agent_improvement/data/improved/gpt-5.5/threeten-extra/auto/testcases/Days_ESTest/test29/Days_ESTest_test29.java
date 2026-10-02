package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test29 extends Days_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void zeroDaysToStringReturnsIsoPeriod() throws Throwable {
        Days zeroDays = Days.ZERO;

        String isoPeriodText = zeroDays.toString();

        assertEquals("P0D", isoPeriodText);
    }
}
