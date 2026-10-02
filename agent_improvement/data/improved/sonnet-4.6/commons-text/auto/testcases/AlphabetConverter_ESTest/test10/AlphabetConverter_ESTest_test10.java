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
public class AlphabetConverter_ESTest_test10 extends AlphabetConverter_ESTest_scaffolding {

    // This long string becomes the encoded value for code point -1.
    // Its length (98 chars) is used as encodedLetterLength, so any
    // decode input shorter than 98 chars will trigger the "unexpected end" error.
    private static final String LONG_ENCODED_VALUE =
        "Must have at least two encoding characters (excluding those in the 'do not encode' list), but has ";

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // Map code point -1 to a very long encoded string (98 characters).
        // createConverterFromMap will set encodedLetterLength = 98.
        HashMap<Integer, String> encodingMap = new HashMap<Integer, String>();
        encodingMap.put(Integer.valueOf(-1), LONG_ENCODED_VALUE);
        AlphabetConverter converter = AlphabetConverter.createConverterFromMap(encodingMap);

        // Attempt to decode a short string that starts with an unmapped character (￿).
        // Since ￿ is not a pass-through character, the decoder tries to read a
        // 98-character group, but the input is far shorter — causing the exception.
        try {
            converter.decode("￿ -> -1\r\n");
            fail("Expecting exception: UnsupportedEncodingException");
        } catch (UnsupportedEncodingException e) {
            //
            // Unexpected end of string while decoding ￿ -> -1\r
            //
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
