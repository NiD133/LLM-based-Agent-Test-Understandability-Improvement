package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test18 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * createConverter must reject a "do not encode" code point that is absent
     * from the encoding alphabet. Here the encoding alphabet is empty, so the
     * single "do not encode" code point (28) cannot be honoured and the method
     * throws IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void createConverterRejectsDoNotEncodeMissingFromEncodingAlphabet() throws Throwable {
        Integer[] originalAlphabet = { Integer.valueOf(28) };
        Integer[] encodingAlphabet = new Integer[0];
        Integer[] doNotEncode = { Integer.valueOf(28) };

        try {
            AlphabetConverter.createConverter(originalAlphabet, encodingAlphabet, doNotEncode);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Can not use 'do not encode' list because encoding alphabet does not contain ''
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
