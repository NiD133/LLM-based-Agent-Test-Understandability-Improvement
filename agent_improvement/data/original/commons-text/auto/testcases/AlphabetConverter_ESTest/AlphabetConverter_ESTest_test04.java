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
public class AlphabetConverter_ESTest_test04 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Integer integer0 = new Integer(2);
        Integer[] integerArray0 = new Integer[4];
        integerArray0[0] = integer0;
        integerArray0[1] = integer0;
        integerArray0[2] = integer0;
        integerArray0[3] = integer0;
        AlphabetConverter alphabetConverter0 = AlphabetConverter.createConverter(integerArray0, integerArray0, integerArray0);
        boolean boolean0 = alphabetConverter0.equals((Object) null);
        assertFalse(boolean0);
    }
}
