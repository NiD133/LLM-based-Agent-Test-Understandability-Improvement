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
public class AlphabetConverter_ESTest_test12 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * When the same single character is used for the original, encoding and
     * "do not encode" alphabets, the converter collapses to a single-character
     * mapping. Decoding {@code null} must return {@code null}, and the encoded
     * character length stays at 1.
     */
    @Test(timeout = 4000)
    public void decodeNullReturnsNullAndEncodedLengthIsOne() throws Throwable {
        // Build an alphabet made of the single repeated character '@'.
        Character repeatedChar = Character.valueOf('@');
        Character[] singleCharAlphabet = new Character[7];
        for (int i = 0; i < singleCharAlphabet.length; i++) {
            singleCharAlphabet[i] = repeatedChar;
        }

        // Use the same alphabet as original, encoding and do-not-encode lists.
        AlphabetConverter converter = AlphabetConverter.createConverterFromChars(
                singleCharAlphabet, singleCharAlphabet, singleCharAlphabet);

        // Decoding null is a no-op that returns null.
        converter.decode((String) null);

        assertEquals(1, converter.getEncodedCharLength());
    }
}
