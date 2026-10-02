package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test14 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * Verifies that createConverter throws NullPointerException when the original
     * or encoding arrays contain null elements (sparse arrays with uninitialized slots).
     * The null entries cause an NPE during internal set iteration when the converter
     * tries to auto-unbox null Integer values to primitive int.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // doNotEncode is empty — all characters in the original alphabet must be encoded
        Integer[] doNotEncode = new Integer[0];

        // Encoding alphabet: 9-element sparse array; only index 1 holds a code point
        Integer[] encodingAlphabet = new Integer[9];
        Integer codePoint3879 = Integer.valueOf(3879);
        encodingAlphabet[1] = codePoint3879;

        // Original alphabet: 8-element sparse array with three non-null code points and null gaps
        Integer[] originalAlphabet = new Integer[8];
        Integer codePoint2 = Integer.valueOf(2);
        originalAlphabet[1] = codePoint2;
        originalAlphabet[2] = codePoint3879;
        Integer codePoint3001 = Integer.valueOf(3001);
        originalAlphabet[3] = codePoint3001;
        // Remaining slots (0, 4-7) are null — these trigger the NullPointerException

        try {
            AlphabetConverter.createConverter(originalAlphabet, encodingAlphabet, doNotEncode);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
