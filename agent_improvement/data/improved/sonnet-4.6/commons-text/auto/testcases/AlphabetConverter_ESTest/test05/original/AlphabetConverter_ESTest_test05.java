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
public class AlphabetConverter_ESTest_test05 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Integer[] integerArray0 = new Integer[0];
        AlphabetConverter alphabetConverter0 = AlphabetConverter.createConverter(integerArray0, integerArray0, integerArray0);
        Integer integer0 = Integer.valueOf(2408);
        boolean boolean0 = alphabetConverter0.equals(integer0);
        assertFalse(boolean0);
    }
}
