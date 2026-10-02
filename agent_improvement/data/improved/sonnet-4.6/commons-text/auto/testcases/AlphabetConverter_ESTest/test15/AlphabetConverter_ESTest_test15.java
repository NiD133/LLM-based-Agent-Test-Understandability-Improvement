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
     * Verifies that when the encoding alphabet is smaller than the original
     * alphabet, the converter requires multi-character encoding (length 2).
     *
     * Setup:
     *   original    = [Z, Z, (, (, =, Z, (, u]  → unique: {Z, (, =, u}  (4 chars)
     *   encoding    = [u, (, (, Z, (, u, Z, u]  → unique: {u, (, Z}     (3 chars)
     *   doNotEncode = [Z]
     *
     * Because encoding (3) < original (4), and only 2 encoding chars are
     * available for remapping (excluding the do-not-encode 'Z'), the converter
     * must use 2-character encoded sequences to cover all original characters.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        // Original alphabet with duplicates; unique set will be {Z, (, =, u}
        Character[] original = new Character[] {
            'Z', 'Z', '(', '(', '=', 'Z', '(', 'u'
        };

        // Encoding alphabet with duplicates; unique set will be {u, (, Z}
        Character[] encoding = new Character[] {
            'u', '(', '(', 'Z', '(', 'u', 'Z', 'u'
        };

        // 'Z' must pass through unchanged; it appears in both original and encoding
        Character[] doNotEncode = new Character[] { 'Z' };

        AlphabetConverter converter =
                AlphabetConverter.createConverterFromChars(original, encoding, doNotEncode);

        assertEquals(2, converter.getEncodedCharLength());
    }
}
