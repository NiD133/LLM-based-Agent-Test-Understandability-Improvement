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
public class AlphabetConverter_ESTest_test18 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        // Code point 28 is the ASCII "File Separator" control character ''
        Integer codePoint28 = Integer.valueOf(28);

        Integer[] originalAlphabet = new Integer[] { codePoint28 };
        Integer[] emptyEncodingAlphabet = new Integer[0];
        // doNotEncode reuses the same single-element array as original
        Integer[] doNotEncodeList = new Integer[] { codePoint28 };

        // '' appears in doNotEncode but is absent from the empty encoding alphabet,
        // so createConverter must reject this configuration.
        try {
            AlphabetConverter.createConverter(originalAlphabet, emptyEncodingAlphabet, doNotEncodeList);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Can not use 'do not encode' list because encoding alphabet does not contain ''
            //
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
