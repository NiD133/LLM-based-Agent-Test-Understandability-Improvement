package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test23 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * equals() must be reflexive: an extended-timestamp field is always
     * equal to itself.
     */
    @Test(timeout = 4000)
    public void equalsIsReflexive() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        boolean equalToItself = extendedTimestamp.equals(extendedTimestamp);

        assertTrue(equalToItself);
    }
}
