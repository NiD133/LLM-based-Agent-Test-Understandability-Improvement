package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test31 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void newInstance_hasAccessTimeBitClear() throws Throwable {
        // A freshly constructed entry has no flags set, so the access-time bit (bit1) must be false.
        X5455_ExtendedTimestamp entry = new X5455_ExtendedTimestamp();

        boolean accessTimePresent = entry.isBit1_accessTimePresent();

        assertFalse("Access time bit should not be set on a default X5455_ExtendedTimestamp", accessTimePresent);
    }
}
