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

    /**
     * Verifies that {@link IOCase#checkIndexOf(String, int, String)} returns -1
     * (the "no match" result) when both the source string and the search string
     * are null, regardless of the start index.
     */
    @Test(timeout = 4000)
    public void checkIndexOf_withNullStringAndNullSearch_returnsNotFound() {
        final IOCase insensitive = IOCase.INSENSITIVE;

        final int matchIndex = insensitive.checkIndexOf(null, 746, null);

        assertEquals(-1, matchIndex);
    }
}
