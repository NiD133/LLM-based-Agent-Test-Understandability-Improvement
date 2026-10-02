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
public class AlphabetConverter_ESTest_test15 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * When the encoding alphabet has fewer distinct characters than the original
     * alphabet, each original character cannot be mapped to a single encoded
     * character. The converter must therefore use multi-character encodings.
     *
     * Here the original alphabet has 4 distinct characters ('Z', '(', '=', 'u')
     * while the encoding alphabet has only 3 distinct characters ('u', '(', 'Z').
     * The converter falls back to a fixed encoded length of 2.
     */
    @Test(timeout = 4000)
    public void encodedCharLengthIsTwoWhenEncodingAlphabetIsSmaller() throws Throwable {
        Character Z = Character.valueOf('Z');
        Character openParen = Character.valueOf('(');
        Character equals = Character.valueOf('=');
        Character u = Character.valueOf('u');

        // Original alphabet (duplicates are ignored): distinct = {Z, (, =, u}
        Character[] original = { Z, Z, openParen, openParen, equals, Z, openParen, u };

        // Encoding alphabet (duplicates are ignored): distinct = {u, (, Z}
        Character[] encoding = { u, openParen, openParen, Z, openParen, u, Z, u };

        // Characters to leave unencoded
        Character[] doNotEncode = { Z };

        AlphabetConverter converter =
                AlphabetConverter.createConverterFromChars(original, encoding, doNotEncode);

        assertEquals(2, converter.getEncodedCharLength());
    }
}
