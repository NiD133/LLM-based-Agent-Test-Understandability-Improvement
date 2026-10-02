package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test09 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09_checkIndexOf_returnsNotFound_whenStartIndexExceedsStringLength() throws Throwable {
        // "System" has length 6; starting the search at index 28 is beyond the string,
        // so checkIndexOf cannot find a match and must return -1.
        IOCase systemCase = IOCase.SYSTEM;
        int result = systemCase.checkIndexOf("System", 28, "rB&T\tpqZ\"");
        assertEquals(-1, result);
    }
}
