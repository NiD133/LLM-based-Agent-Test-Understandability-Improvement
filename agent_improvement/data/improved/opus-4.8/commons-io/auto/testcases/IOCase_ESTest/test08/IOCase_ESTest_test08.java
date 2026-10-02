package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test08 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that {@link IOCase#checkIndexOf(String, int, String)} returns -1
     * when the search string is null, regardless of the source string or start index.
     */
    @Test(timeout = 4000)
    public void checkIndexOf_withNullSearchString_returnsNotFound() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;

        int matchIndex = systemCase.checkIndexOf("System", 24, (String) null);

        assertEquals("A null search string should never match", -1, matchIndex);
    }
}
