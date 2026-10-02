package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test06 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that {@link IOCase#checkIndexOf(String, int, String)} returns -1
     * when the search term does not occur in the text at or after the start index.
     */
    @Test(timeout = 4000)
    public void checkIndexOf_returnsMinusOne_whenSearchTermNotFound() throws Throwable {
        IOCase caseInsensitive = IOCase.INSENSITIVE;
        String text = " without breaking the first codepoint or grapheme cluster";
        int searchFromIndex = 15;
        String missingSearchTerm = "!8-T";

        int matchIndex = caseInsensitive.checkIndexOf(text, searchFromIndex, missingSearchTerm);

        assertEquals(-1, matchIndex);
    }
}
