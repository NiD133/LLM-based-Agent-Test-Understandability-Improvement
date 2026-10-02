package org.apache.commons.compress.archivers.zip;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test31 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * A freshly constructed extended-timestamp field has no flags set, so the
     * access-time bit (bit 1) should report as absent.
     */
    @Test(timeout = 4000)
    public void accessTimeIsAbsentByDefault() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        boolean accessTimePresent = extendedTimestamp.isBit1_accessTimePresent();

        assertFalse(accessTimePresent);
    }
}
