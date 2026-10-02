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
public class AlphabetConverter_ESTest_test19 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * Verifies that createConverter throws IllegalArgumentException when the
     * 'do not encode' list contains a code point () that is absent from
     * the original alphabet (which contains only null entries).
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        // Original alphabet: 3-element array, all entries are null (no valid code points)
        Integer[] originalAlphabet = new Integer[3];

        // Encoding alphabet: 8-element array; first entry is code point 2 (, STX control char)
        Integer[] encodingAlphabet = new Integer[8];
        Integer codePoint2 = new Integer(2);
        encodingAlphabet[0] = codePoint2;

        // Using encodingAlphabet as both the encoding AND the 'do not encode' list.
        // Since code point 2 is in 'do not encode' but not in originalAlphabet,
        // createConverter must reject this configuration.
        try {
            AlphabetConverter.createConverter(originalAlphabet, encodingAlphabet, encodingAlphabet);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Can not use 'do not encode' list because original alphabet does not contain ''
            //
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
