package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test40 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link WordUtils#wrap(String, int)} breaks the input on spaces so
     * that each resulting line is at most {@code wrapLength} characters long.
     *
     * <p>With a wrap length of 2, every space-separated word is short enough to stay on
     * its own line, so the text is wrapped after each word. New lines are inserted using
     * the mocked system line separator ("\r\n").</p>
     */
    @Test(timeout = 4000)
    public void wrapWithLengthTwoPutsEachWordOnItsOwnLine() throws Throwable {
        String textToWrap = "upper value cannot be less than -1";
        int wrapLength = 2;

        String wrappedText = WordUtils.wrap(textToWrap, wrapLength);

        assertNotNull(wrappedText);
        assertEquals("upper\r\nvalue\r\ncannot\r\nbe\r\nless\r\nthan\r\n-1", wrappedText);
    }
}
