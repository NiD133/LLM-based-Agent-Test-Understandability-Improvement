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

    /**
     * Verifies that checkIndexOf returns -1 when the start index lies beyond
     * any possible match position. Searching "System" (length 6) starting at
     * index 28 leaves no room for the 9-character search string, so no match
     * can occur and -1 is returned.
     */
    @Test(timeout = 4000)
    public void checkIndexOfReturnsMinusOneWhenStartIndexExceedsText() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;

        int matchIndex = systemCase.checkIndexOf("System", 28, "rB&T\tpqZ\"");

        assertEquals(-1, matchIndex);
    }
}
