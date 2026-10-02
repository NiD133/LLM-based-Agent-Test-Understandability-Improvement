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
     * Verifies that {@link WordUtils#wrap(String, int, String, boolean, String)} returns the
     * input text unchanged when no wrapping is needed.
     *
     * <p>Here the negative wrap length is internally clamped to 1, and the {@code wrapOn}
     * pattern is the entire input string. Since the input itself never appears again after
     * its single (leading) occurrence, there is nothing to break on, so the original text is
     * returned as-is.</p>
     */
    @Test(timeout = 4000)
    public void wrapReturnsInputUnchangedWhenNoBreakPointFound() throws Throwable {
        String text = ":9~&Gxna!";
        int negativeWrapLength = -182;
        String useSystemLineSeparator = null;
        boolean wrapLongWords = false;
        String wrapOn = ":9~&Gxna!";

        String wrapped = WordUtils.wrap(text, negativeWrapLength, useSystemLineSeparator, wrapLongWords, wrapOn);

        assertEquals(":9~&Gxna!", wrapped);
    }
}
