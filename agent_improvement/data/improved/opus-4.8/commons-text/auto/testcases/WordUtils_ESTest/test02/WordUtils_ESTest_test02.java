package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test02 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies {@link WordUtils#wrap(String, int, String, boolean, String)} when the
     * regex used to find break points ({@code wrapOn}) matches a zero-width position.
     *
     * <p>The pattern {@code "\b.*"} matches at the word boundary before each character,
     * so the wrapper inserts the new-line string {@code "\b.*"} between every character
     * of {@code "3^s.6"}, producing {@code "3\b.*^\b.*s\b.*.\b.*6"}.</p>
     */
    @Test(timeout = 4000)
    public void wrapInsertsNewLineStringBetweenEveryCharacter() throws Throwable {
        final String input = "3^s.6";
        final int wrapLength = 0;          // values below 1 are treated as 1
        final String newLineString = "\b.*";
        final boolean wrapLongWords = true;
        final String wrapOn = "\b.*";      // regex marking where breaks may occur

        String wrapped = WordUtils.wrap(input, wrapLength, newLineString, wrapLongWords, wrapOn);

        assertEquals("3\b.*^\b.*s\b.*.\b.*6", wrapped);
    }
}
