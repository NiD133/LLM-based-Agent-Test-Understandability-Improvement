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
public class AlphabetConverter_ESTest_test13 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * When the encoding alphabet (unique: T, q, u) is smaller than the original
     * alphabet (unique: T, q, =, u), and only one character ('u') is excluded from
     * encoding, the converter must use 2-character encoded sequences, so
     * getEncodedCharLength() should return 2.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        Character charT = Character.valueOf('T');
        Character charQ = Character.valueOf('q');
        Character charEquals = Character.valueOf('=');
        Character charU = Character.valueOf('u');

        // Original alphabet: [T, T, T, q, =, T, T, u] — unique chars: {T, q, =, u}
        Character[] originalAlphabet = new Character[8];
        originalAlphabet[0] = charT;
        originalAlphabet[1] = charT;
        originalAlphabet[2] = charT;
        originalAlphabet[3] = charQ;
        originalAlphabet[4] = charEquals;
        originalAlphabet[5] = charT;
        originalAlphabet[6] = charT;
        originalAlphabet[7] = charU;

        // Encoding alphabet: [u, u, q, T, T, u, T, u] — unique chars: {u, q, T}
        Character[] encodingAlphabet = new Character[8];
        encodingAlphabet[0] = charU;
        encodingAlphabet[1] = charU;
        encodingAlphabet[2] = charQ;
        encodingAlphabet[3] = charT;
        encodingAlphabet[4] = charT;
        encodingAlphabet[5] = charU;
        encodingAlphabet[6] = charT;
        encodingAlphabet[7] = charU;

        // Characters to pass through unencoded (must appear in both alphabets)
        Character[] doNotEncode = new Character[] { charU };

        AlphabetConverter converter = AlphabetConverter.createConverterFromChars(
                originalAlphabet, encodingAlphabet, doNotEncode);

        // Encoding alphabet (3 unique) < original alphabet (4 unique), so 2-char encoding is required
        assertEquals(2, converter.getEncodedCharLength());
    }
}
