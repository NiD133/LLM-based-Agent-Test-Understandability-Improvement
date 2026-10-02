package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test09 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * Verifies that a converter built from an original-to-encoded map can decode
     * an encoded string back to the original alphabet.
     *
     * <p>The map says: code point 1 (the character U+0001) is encoded as the
     * multi-character token "qor_5~yr2yEtVdG{". Decoding that token must therefore
     * yield the single original character U+0001.</p>
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Original code point 1 maps to this encoded token.
        final Integer originalCodePoint = 1;
        final String encodedToken = "qor_5~yr2yEtVdG{";

        Map<Integer, String> originalToEncoded = new HashMap<Integer, String>();
        originalToEncoded.put(originalCodePoint, encodedToken);

        AlphabetConverter converter =
                AlphabetConverter.createConverterFromMap(originalToEncoded);
        assertEquals(1, originalToEncoded.size());

        // Decoding the encoded token reproduces the original character U+0001.
        String decoded = converter.decode(encodedToken);
        assertEquals("", decoded);
    }
}
