package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test06 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link WordUtils#wrap(String, int, String, boolean)} returns
     * {@code null} when the input string is {@code null}, regardless of the
     * wrap length, new-line string, or wrap-long-words flag.
     */
    @Test(timeout = 4000)
    public void wrap_withNullInput_returnsNull() throws Throwable {
        String nullInput = null;
        int wrapLength = 296;
        String newLineString = ";)(5b_Sh4o|A8@";
        boolean wrapLongWords = true;

        String result = WordUtils.wrap(nullInput, wrapLength, newLineString, wrapLongWords);

        assertNull("wrap should return null for a null input string", result);
    }
}
