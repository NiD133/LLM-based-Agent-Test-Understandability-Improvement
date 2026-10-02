package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test24 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * equals() must return false when compared against an object that is not an
     * X5455_ExtendedTimestamp instance (here, a plain java.lang.Object).
     */
    @Test(timeout = 4000)
    public void equalsReturnsFalseForUnrelatedObject() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();
        Object unrelatedObject = new Object();

        boolean isEqual = extendedTimestamp.equals(unrelatedObject);

        assertFalse(isEqual);
    }
}
