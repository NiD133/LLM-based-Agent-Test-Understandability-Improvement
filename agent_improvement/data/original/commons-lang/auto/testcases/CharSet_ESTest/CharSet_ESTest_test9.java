package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test9 extends CharSet_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test9() throws Throwable {
        String[] stringArray0 = new String[1];
        CharSet charSet0 = CharSet.getInstance(stringArray0);
        Set<CharRange> set0 = charSet0.getCharRanges();
        assertEquals(0, set0.size());
    }
}
