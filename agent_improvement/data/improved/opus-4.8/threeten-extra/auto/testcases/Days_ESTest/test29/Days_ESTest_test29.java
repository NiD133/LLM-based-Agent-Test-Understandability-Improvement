package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test29 extends Days_ESTest_scaffolding {

    /**
     * Verifies that the zero-day amount renders in ISO-8601 period
     * format as "P0D".
     */
    @Test(timeout = 4000)
    public void zeroDays_toString_returnsP0D() throws Throwable {
        Days zeroDays = Days.ZERO;

        String isoFormat = zeroDays.toString();

        assertEquals("P0D", isoFormat);
    }
}
