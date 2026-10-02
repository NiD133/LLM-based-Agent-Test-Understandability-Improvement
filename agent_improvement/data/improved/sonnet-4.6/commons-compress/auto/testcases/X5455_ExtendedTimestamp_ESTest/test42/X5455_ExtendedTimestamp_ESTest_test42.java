package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test42 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test42_newInstanceHasZeroFlags() throws Throwable {
        // A freshly constructed X5455_ExtendedTimestamp should have no timestamps
        // present, so all flag bits (modify=bit0, access=bit1, create=bit2) must be 0.
        X5455_ExtendedTimestamp timestamp = new X5455_ExtendedTimestamp();

        byte flags = timestamp.getFlags();

        assertEquals("Default flags should be 0 (no timestamps present)", (byte) 0, flags);
    }
}
