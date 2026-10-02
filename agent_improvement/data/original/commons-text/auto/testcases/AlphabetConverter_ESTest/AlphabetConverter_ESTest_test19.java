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

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        Integer[] integerArray0 = new Integer[3];
        Integer[] integerArray1 = new Integer[8];
        Integer integer0 = new Integer(2);
        integerArray1[0] = integer0;
        // Undeclared exception!
        try {
            AlphabetConverter.createConverter(integerArray0, integerArray1, integerArray1);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Can not use 'do not encode' list because original alphabet does not contain '\u0002'
            //
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
