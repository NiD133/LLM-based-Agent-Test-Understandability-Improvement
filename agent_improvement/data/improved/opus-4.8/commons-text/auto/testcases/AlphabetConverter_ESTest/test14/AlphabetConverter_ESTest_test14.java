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
public class AlphabetConverter_ESTest_test14 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * createConverter unboxes each encoding code point to a primitive int while
     * building the encoder. When the encoding alphabet contains a null entry
     * (here the unset slots of a partially populated array), that unboxing throws
     * a NullPointerException with no message, originating from AlphabetConverter.
     */
    @Test(timeout = 4000)
    public void createConverterWithNullEncodingCodePointThrowsNPE() throws Throwable {
        // No "do not encode" characters.
        Integer[] doNotEncode = new Integer[0];

        // Encoding alphabet: only one real code point, the rest of the slots stay null.
        Integer[] encoding = new Integer[9];
        encoding[1] = Integer.valueOf(3879);

        // Original alphabet: a few code points, with the remaining slots left null.
        Integer[] original = new Integer[8];
        original[1] = Integer.valueOf(2);
        original[2] = Integer.valueOf(3879);
        original[3] = Integer.valueOf(3001);

        // The null entries in the encoding alphabet trigger a NullPointerException
        // when the converter unboxes the code points.
        try {
            AlphabetConverter.createConverter(original, encoding, doNotEncode);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // no message in exception (getMessage() returned null)
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
