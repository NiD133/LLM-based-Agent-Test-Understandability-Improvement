package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test26 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that the ISO-8601 representation of a zero-minute amount is "PT0M".
     */
    @Test(timeout = 4000)
    public void zeroMinutes_toString_isPT0M() throws Throwable {
        Minutes zeroMinutes = Minutes.ZERO;

        String iso8601 = zeroMinutes.toString();

        assertEquals("PT0M", iso8601);
    }
}
