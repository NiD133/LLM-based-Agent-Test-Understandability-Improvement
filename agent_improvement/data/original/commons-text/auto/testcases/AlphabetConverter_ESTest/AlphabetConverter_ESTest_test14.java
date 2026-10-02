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
        Integer[] integerArray0 = new Integer[0];
        Integer[] integerArray1 = new Integer[9];
        Integer[] integerArray2 = new Integer[8];
        Integer integer0 = new Integer(3879);
        integerArray1[1] = integer0;
        Integer integer1 = Integer.valueOf(2);
        integerArray2[1] = integer1;
        integerArray2[2] = integer0;
        Integer integer2 = new Integer(3001);
        integerArray2[3] = integer2;
        // Undeclared exception!
        try {
            AlphabetConverter.createConverter(integerArray2, integerArray1, integerArray0);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
