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

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        Integer[] doNotEncode = new Integer[0];
        Integer[] encodingAlphabet = new Integer[9];
        Integer[] originalAlphabet = new Integer[8];

        Integer sharedCodePoint = new Integer(3879);
        encodingAlphabet[1] = sharedCodePoint;

        Integer twoCodePoint = Integer.valueOf(2);
        originalAlphabet[1] = twoCodePoint;
        originalAlphabet[2] = sharedCodePoint;

        Integer additionalOriginalCodePoint = new Integer(3001);
        originalAlphabet[3] = additionalOriginalCodePoint;

        // Undeclared exception!
        try {
            AlphabetConverter.createConverter(originalAlphabet, encodingAlphabet, doNotEncode);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
