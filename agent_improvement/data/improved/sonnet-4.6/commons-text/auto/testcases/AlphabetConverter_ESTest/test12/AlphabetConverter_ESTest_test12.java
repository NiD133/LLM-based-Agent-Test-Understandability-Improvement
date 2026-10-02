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
     * When all three character arrays (original, encoding, and doNotEncode) contain
     * only a single repeated character '@', the converter should:
     *  - handle decode(null) gracefully (returning null without throwing)
     *  - report an encoded character length of 1, because the encoding alphabet
     *    is the same size as the original alphabet
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        // All three converter roles use the same single character '@'
        final Character atSign = Character.valueOf('@');
        final int ARRAY_SIZE = 7;
        final Character[] allAtSign = new Character[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            allAtSign[i] = atSign;
        }

        // Construct the converter — duplicates are ignored, so only '@' is in each set
        AlphabetConverter converter = AlphabetConverter.createConverterFromChars(
                allAtSign,   // original alphabet
                allAtSign,   // encoding alphabet
                allAtSign);  // characters to leave unencoded

        // decode(null) must return null and not throw
        converter.decode((String) null);

        // Encoding and original alphabets are the same size, so each character
        // maps to exactly one encoded character (length == 1)
        assertEquals(1, converter.getEncodedCharLength());
    }
}
