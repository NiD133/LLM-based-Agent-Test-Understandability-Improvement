package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test03 extends WordUtils_ESTest_scaffolding {

    /**
     * When wrapLength is negative, the wrap method treats it as 1.
     * Because the wrapOn pattern equals the entire input and does not appear
     * as a sub-sequence within the column window, no wrap point is found and
     * the original string is returned unchanged.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        final String input        = ":9~&Gxna!";
        final int    wrapLength   = -182;       // negative → clamped to 1 internally
        final String newLineStr   = null;        // null → uses system line separator
        final boolean wrapLong   = false;
        final String wrapOn      = ":9~&Gxna!"; // pattern same as input; never matches within the column window

        String result = WordUtils.wrap(input, wrapLength, newLineStr, wrapLong, wrapOn);

        assertEquals(input, result);
    }
}
