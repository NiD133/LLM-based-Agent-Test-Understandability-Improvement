package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test35 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * A freshly constructed extended-timestamp field carries no flags, so the
     * "modify time present" bit (bit 0) must report as not set.
     */
    @Test(timeout = 4000)
    public void newInstanceHasNoModifyTimePresent() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        boolean modifyTimePresent = extendedTimestamp.isBit0_modifyTimePresent();

        assertFalse(modifyTimePresent);
    }
}
