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
     * A code point may only appear in the 'do not encode' list when it is also
     * present in the encoding alphabet. Here the code point 28 ('') is in
     * both the original and the 'do not encode' lists, but the encoding alphabet
     * is empty, so {@link AlphabetConverter#createConverter} must reject it.
     */
    @Test(timeout = 4000)
    public void createConverterRejectsDoNotEncodeCharMissingFromEncoding() throws Throwable {
        Integer codePoint = Integer.valueOf(28);
        Integer[] original = { codePoint };
        Integer[] encoding = new Integer[0];
        Integer[] doNotEncode = { codePoint };

        try {
            AlphabetConverter.createConverter(original, encoding, doNotEncode);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Can not use 'do not encode' list because encoding alphabet does not contain ''
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
