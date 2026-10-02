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
     * A code point listed in 'do not encode' must also appear in the encoding alphabet.
     * Here the encoding alphabet is empty, so requesting to leave code point 28 unencoded
     * is rejected with an IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void createConverterRejectsDoNotEncodeMissingFromEncoding() throws Throwable {
        Integer[] originalAlphabet = { 28 };
        Integer[] emptyEncodingAlphabet = new Integer[0];
        Integer[] doNotEncode = { 28 };

        try {
            AlphabetConverter.createConverter(originalAlphabet, emptyEncodingAlphabet, doNotEncode);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Can not use 'do not encode' list because encoding alphabet does not contain ''
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
