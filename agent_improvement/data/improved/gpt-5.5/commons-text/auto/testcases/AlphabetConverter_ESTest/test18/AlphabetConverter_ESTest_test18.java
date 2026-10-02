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

    private static final Integer CONTROL_CHARACTER_CODE_POINT = new Integer(28);

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        Integer[] emptyEncodingAlphabet = new Integer[0];
        Integer[] originalAlphabet = new Integer[1];
        originalAlphabet[0] = CONTROL_CHARACTER_CODE_POINT;
        Integer[] doNotEncodeAlphabet = originalAlphabet;

        try {
            AlphabetConverter.createConverter(originalAlphabet, emptyEncodingAlphabet, doNotEncodeAlphabet);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
