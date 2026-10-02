package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test18 extends AlphabetConverter_ESTest_scaffolding {

    // Unicode codepoint 28 corresponds to the control character '' (File Separator)
    private static final int CODEPOINT_FILE_SEPARATOR = 28;

    /**
     * Verifies that createConverter throws IllegalArgumentException when a character
     * in the 'doNotEncode' list is absent from the encoding alphabet.
     *
     * Setup:
     *   original    = [28]  (one character: '')
     *   encoding    = []    (empty — does NOT contain '')
     *   doNotEncode = [28]  (requests '' to pass through unchanged)
     *
     * Because '' must appear in the encoding alphabet for pass-through to work,
     * the converter factory must reject this configuration.
     */
    @Test(timeout = 4000)
    public void test18() throws Throwable {
        Integer[] originalAlphabet   = { CODEPOINT_FILE_SEPARATOR };
        Integer[] encodingAlphabet   = new Integer[0];
        Integer[] doNotEncodeChars   = { CODEPOINT_FILE_SEPARATOR };

        try {
            AlphabetConverter.createConverter(originalAlphabet, encodingAlphabet, doNotEncodeChars);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
