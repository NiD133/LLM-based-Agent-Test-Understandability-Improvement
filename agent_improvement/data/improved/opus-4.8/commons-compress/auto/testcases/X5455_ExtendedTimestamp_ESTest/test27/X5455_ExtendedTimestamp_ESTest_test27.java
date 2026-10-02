package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test27 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * A freshly constructed timestamp field can be cloned, and the ACCESS_TIME_BIT
     * constant keeps its documented value of 2.
     */
    @Test(timeout = 4000)
    public void cloneSucceedsAndAccessTimeBitConstantIsTwo() throws Throwable {
        X5455_ExtendedTimestamp original = new X5455_ExtendedTimestamp();

        X5455_ExtendedTimestamp clone = (X5455_ExtendedTimestamp) original.clone();

        assertEquals((byte) 2, X5455_ExtendedTimestamp.ACCESS_TIME_BIT);
    }
}
