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
public class AlphabetConverter_ESTest_test16 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * When the encoding alphabet is the same size as (or larger than) the original
     * alphabet, each source character maps to exactly one encoded character, so
     * getEncodedCharLength() must return 1.
     */
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        // Original and encoding alphabets: ['V', '/'] — same array used for both
        Character[] originalAndEncoding = new Character[2];
        Character charV = Character.valueOf('V');
        originalAndEncoding[0] = charV;
        Character charSlash = Character.valueOf('/');
        originalAndEncoding[1] = charSlash;

        // 'V' is listed as "do not encode", so it passes through unchanged
        Character[] doNotEncode = new Character[1];
        doNotEncode[0] = charV;

        AlphabetConverter converter = AlphabetConverter.createConverterFromChars(
                originalAndEncoding, originalAndEncoding, doNotEncode);

        // Encoding alphabet size == original alphabet size → one-to-one mapping → length 1
        assertEquals(1, converter.getEncodedCharLength());
    }
}
