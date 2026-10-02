package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test11 extends CharSetUtils_ESTest_scaffolding {

    /**
     * Verifies that count() correctly counts characters in a string when the
     * CharSet is defined by a sparse set array (only the first element is set;
     * the remaining three elements are null and are ignored by the implementation).
     *
     * The CharSet pattern "!NIzU+h g./^6" matches 12 of the 13 characters in
     * the input string "!NIzU+h g./^6".
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        String inputString = "!NIzU+h g./^6";

        // A 4-element set array where only index 0 carries the pattern;
        // indices 1-3 remain null and are treated as empty by CharSetUtils.
        String[] charSetArray = new String[4];
        charSetArray[0] = "!NIzU+h g./^6";

        int matchingCharCount = CharSetUtils.count(inputString, charSetArray);

        assertEquals(12, matchingCharCount);
    }
}
