package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test04 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that setFlags stores the flags byte verbatim, so that a later
     * getFlags returns exactly what was set, and that toString can be invoked
     * safely once a flag bit (here the access-time bit, value 2) is set.
     */
    @Test(timeout = 4000)
    public void settingAccessTimeFlagIsReadBackByGetFlags() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        extendedTimestamp.setFlags(X5455_ExtendedTimestamp.ACCESS_TIME_BIT);
        extendedTimestamp.toString();

        assertEquals(X5455_ExtendedTimestamp.ACCESS_TIME_BIT, extendedTimestamp.getFlags());
    }
}
