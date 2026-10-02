package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test07 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that checkIndexOf locates the search string at the very start of
     * the text. The text and the search string are identical ("LINUX"), so the
     * only possible match is at index 0. The (negative) start index is clamped
     * by the search loop, so the match is still found at index 0.
     */
    @Test(timeout = 4000)
    public void checkIndexOf_whenTextEqualsSearch_returnsZero() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;

        int matchIndex = systemCase.checkIndexOf("LINUX", -1117, "LINUX");

        assertEquals(0, matchIndex);
    }
}
