package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test13 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * When the encoding alphabet is smaller than the original alphabet, each
     * original character must be encoded with more than one character. Here the
     * original alphabet has 4 distinct characters {T, q, =, u} while the encoding
     * alphabet has only 3 distinct characters {u, q, T}, so the converter falls
     * back to a fixed encoded length of 2.
     */
    @Test(timeout = 4000)
    public void createConverterFromChars_withSmallerEncodingAlphabet_usesEncodedCharLengthOfTwo() throws Throwable {
        // Original alphabet: distinct characters are T, q, =, u (4 distinct)
        Character[] original = { 'T', 'T', 'T', 'q', '=', 'T', 'T', 'u' };

        // Encoding alphabet: distinct characters are u, q, T (3 distinct)
        Character[] encoding = { 'u', 'u', 'q', 'T', 'T', 'u', 'T', 'u' };

        // Characters left unencoded; 'u' appears in both alphabets above
        Character[] doNotEncode = { 'u' };

        AlphabetConverter converter =
                AlphabetConverter.createConverterFromChars(original, encoding, doNotEncode);

        assertEquals(2, converter.getEncodedCharLength());
    }
}
