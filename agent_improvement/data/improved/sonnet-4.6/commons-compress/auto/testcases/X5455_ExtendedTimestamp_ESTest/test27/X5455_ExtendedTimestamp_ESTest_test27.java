package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test27 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test27() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();
        X5455_ExtendedTimestamp clonedTimestamp = (X5455_ExtendedTimestamp) extendedTimestamp.clone();
        assertEquals("ACCESS_TIME_BIT should represent bit 1 (decimal value 2)",
                (byte) 2, X5455_ExtendedTimestamp.ACCESS_TIME_BIT);
    }
}
