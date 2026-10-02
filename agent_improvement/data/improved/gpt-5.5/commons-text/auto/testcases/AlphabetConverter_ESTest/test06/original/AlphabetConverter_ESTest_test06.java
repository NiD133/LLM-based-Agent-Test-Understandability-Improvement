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
public class AlphabetConverter_ESTest_test06 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Integer[] integerArray0 = new Integer[1];
        Integer integer0 = new Integer(8);
        integerArray0[0] = integer0;
        AlphabetConverter alphabetConverter0 = AlphabetConverter.createConverter(integerArray0, integerArray0, integerArray0);
        try {
            alphabetConverter0.encode("\b -> 8\r\n");
            fail("Expecting exception: UnsupportedEncodingException");
        } catch (UnsupportedEncodingException e) {
            //
            // Couldn't find encoding for ' ' in \b -> 8\r
            //
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
