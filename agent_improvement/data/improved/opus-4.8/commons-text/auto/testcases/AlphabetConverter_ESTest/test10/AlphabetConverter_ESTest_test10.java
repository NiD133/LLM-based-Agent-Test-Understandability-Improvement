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

    /**
     * Builds a converter whose only encoding maps the code point -1 to a long
     * string. Because that encoded value is long, the converter's encoded-letter
     * length is large. Decoding a short input that does not start with a known
     * encoded letter therefore runs past the end of the string before a full
     * encoded group can be read, which is reported as an
     * {@link UnsupportedEncodingException}.
     */
    @Test(timeout = 4000)
    public void decodeShorterThanEncodedLengthThrowsUnsupportedEncoding() throws Throwable {
        // A single mapping: code point -1 -> a long encoded string.
        HashMap<Integer, String> originalToEncoded = new HashMap<Integer, String>();
        String longEncodedValue =
                "Must have at least two encoding characters (excluding those in the 'do not encode' list), but has ";
        originalToEncoded.put(Integer.valueOf(-1), longEncodedValue);

        AlphabetConverter converter = AlphabetConverter.createConverterFromMap(originalToEncoded);

        // The input is far shorter than the converter's encoded-letter length,
        // so decoding cannot read a full encoded group and must fail.
        try {
            converter.decode("￿ -> -1\r\n");
            fail("Expecting exception: UnsupportedEncodingException");
        } catch (UnsupportedEncodingException e) {
            // Unexpected end of string while decoding "￿ -> -1\r".
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
