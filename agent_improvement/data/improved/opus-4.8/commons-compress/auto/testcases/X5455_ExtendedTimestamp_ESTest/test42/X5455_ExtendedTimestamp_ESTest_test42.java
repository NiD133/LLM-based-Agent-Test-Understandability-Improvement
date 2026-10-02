package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test42 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * A freshly constructed extended-timestamp field has no timestamps set,
     * so its flags byte should start out as zero (no bits set).
     */
    @Test(timeout = 4000)
    public void newFieldHasZeroFlags() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        byte flags = extendedTimestamp.getFlags();

        assertEquals((byte) 0, flags);
    }
}
