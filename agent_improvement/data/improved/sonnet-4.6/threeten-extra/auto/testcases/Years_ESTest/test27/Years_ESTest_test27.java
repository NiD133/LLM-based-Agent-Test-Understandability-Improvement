package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test27 extends Years_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_hashCode_ofOneYear_returnsOne() throws Throwable {
        // Years.ONE represents 1 year; hashCode() returns the year value directly
        Years oneYear = Years.ONE;
        int hashCode = oneYear.hashCode();
        assertEquals(1, hashCode);
    }
}
