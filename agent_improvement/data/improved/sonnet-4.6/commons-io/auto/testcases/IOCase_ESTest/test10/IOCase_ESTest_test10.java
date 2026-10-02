package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test10 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10_checkIndexOf_returnsNegativeOne_whenBothStrAndSearchAreNull() throws Throwable {
        // checkIndexOf returns -1 immediately when either input is null (no search is performed)
        int result = IOCase.INSENSITIVE.checkIndexOf(null, 746, null);
        assertEquals(-1, result);
    }
}
