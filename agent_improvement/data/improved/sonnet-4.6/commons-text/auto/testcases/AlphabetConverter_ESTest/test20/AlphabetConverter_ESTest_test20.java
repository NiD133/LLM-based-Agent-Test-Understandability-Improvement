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
public class AlphabetConverter_ESTest_test20 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * When the original and encoding alphabets are the same single-character set,
     * no multi-character encoding is needed, so the encoded char length should be 1.
     */
    @Test(timeout = 4000)
    public void test_encodedCharLength_isOne_whenOriginalAndEncodingAlphabetsAreIdentical() throws Throwable {
        Character charT = Character.valueOf('T');
        Character[] singleCharAlphabet = new Character[3];
        singleCharAlphabet[0] = charT;
        singleCharAlphabet[1] = charT;
        singleCharAlphabet[2] = singleCharAlphabet[0];

        AlphabetConverter converter = AlphabetConverter.createConverterFromChars(
                singleCharAlphabet, singleCharAlphabet, (Character[]) null);

        assertEquals(1, converter.getEncodedCharLength());
    }
}
