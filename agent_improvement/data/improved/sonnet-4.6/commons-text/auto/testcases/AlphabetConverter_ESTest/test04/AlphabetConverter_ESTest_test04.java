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
public class AlphabetConverter_ESTest_test04 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * Verifies that equals(null) returns false for a converter created with a
     * single-element alphabet (code point 2) shared across original, encoding,
     * and doNotEncode arrays.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Integer codePoint = new Integer(2);

        // Build a 4-element array where every slot holds the same code point.
        // Using the same array for original, encoding, and doNotEncode means
        // the sole character is both the source alphabet and a pass-through char.
        Integer[] singleCharAlphabet = new Integer[4];
        singleCharAlphabet[0] = codePoint;
        singleCharAlphabet[1] = codePoint;
        singleCharAlphabet[2] = codePoint;
        singleCharAlphabet[3] = codePoint;

        AlphabetConverter converter = AlphabetConverter.createConverter(
                singleCharAlphabet, singleCharAlphabet, singleCharAlphabet);

        // A valid converter should never be equal to null.
        boolean isEqualToNull = converter.equals((Object) null);
        assertFalse(isEqualToNull);
    }
}
