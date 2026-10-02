package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test17 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * The encoding alphabet must contain at least two characters once the
     * 'do not encode' characters are removed. Here the encoding alphabet is
     * {@code {'Z'}} and the 'do not encode' list is also {@code {'Z'}}, so the
     * usable encoding alphabet is empty (size 0). Creating the converter must
     * therefore fail with an IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void createConverterFromCharsRejectsTooFewEncodingChars() throws Throwable {
        // Two distinct characters to encode.
        Character[] originalAlphabet = { 'Z', '=' };
        // Single-character encoding alphabet, reused as the 'do not encode' list.
        Character[] encodingAlphabet = { 'Z' };

        try {
            AlphabetConverter.createConverterFromChars(
                    originalAlphabet, encodingAlphabet, encodingAlphabet);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "Must have at least two encoding characters
            // (excluding those in the 'do not encode' list), but has 0"
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
