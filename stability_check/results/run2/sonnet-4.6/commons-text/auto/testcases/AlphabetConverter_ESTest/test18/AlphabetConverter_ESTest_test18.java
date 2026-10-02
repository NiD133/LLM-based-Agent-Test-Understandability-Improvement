package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test18 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * Verifies that createConverter throws IllegalArgumentException when a character
     * in the doNotEncode list does not appear in the encoding alphabet.
     *
     * Setup:
     *   original    = [28]  (U+001C FILE SEPARATOR)
     *   encoding    = []    (empty — does not contain code point 28)
     *   doNotEncode = [28]  (same as original, but missing from encoding)
     *
     * The converter cannot honour the "do not encode" request for U+001C because
     * U+001C is absent from the encoding alphabet, so an IllegalArgumentException
     * must be raised.
     */
    @Test(timeout = 4000)
    public void test18() throws Throwable {
        // U+001C (FILE SEPARATOR), the only character in the original alphabet
        final int FILE_SEPARATOR_CODE_POINT = 28;

        Integer[] originalAlphabet  = new Integer[] { FILE_SEPARATOR_CODE_POINT };
        Integer[] encodingAlphabet  = new Integer[0];   // intentionally empty
        Integer[] doNotEncodeChars  = new Integer[] { FILE_SEPARATOR_CODE_POINT };

        // doNotEncodeChars contains U+001C, but encodingAlphabet is empty,
        // so the converter cannot satisfy the "do not encode" constraint.
        try {
            AlphabetConverter.createConverter(originalAlphabet, encodingAlphabet, doNotEncodeChars);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Can not use 'do not encode' list because encoding alphabet does not contain ''
            //
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
