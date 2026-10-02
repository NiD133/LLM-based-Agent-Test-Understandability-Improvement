package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test29 extends WordUtils_ESTest_scaffolding {

    /**
     * When the input contains no space, {@code abbreviate} truncates it to the
     * {@code upper} limit and, because truncation actually occurred, appends the
     * {@code appendToEnd} string.
     *
     * Here the text has no spaces, so the first 7 characters ("b6Qf>#}") are kept
     * and the full appendToEnd string is added to the end.
     */
    @Test(timeout = 4000)
    public void abbreviateWithoutSpaceTruncatesAndAppendsSuffix() throws Throwable {
        String text = "b6Qf>#}c]W/%TXT9},Eb";
        int lowerLimit = 7;
        int upperLimit = 7;
        String appendToEnd = text;

        String abbreviated = WordUtils.abbreviate(text, lowerLimit, upperLimit, appendToEnd);

        String firstSevenChars = "b6Qf>#}";
        assertEquals(firstSevenChars + appendToEnd, abbreviated);
    }
}
