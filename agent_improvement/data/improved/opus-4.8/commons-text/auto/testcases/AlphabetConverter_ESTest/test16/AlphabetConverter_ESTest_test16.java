package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test16 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * When the encoding alphabet is at least as large as the original alphabet,
     * each original character maps to a single encoded character, so the encoded
     * char length is 1.
     */
    @Test(timeout = 4000)
    public void encodedCharLengthIsOneWhenEncodingAlphabetCoversOriginal() throws Throwable {
        Character[] originalAlphabet = {'V', '/'};
        Character[] encodingAlphabet = {'V', '/'};
        Character[] doNotEncode = {'V'};

        AlphabetConverter converter =
                AlphabetConverter.createConverterFromChars(originalAlphabet, encodingAlphabet, doNotEncode);

        assertEquals(1, converter.getEncodedCharLength());
    }
}
