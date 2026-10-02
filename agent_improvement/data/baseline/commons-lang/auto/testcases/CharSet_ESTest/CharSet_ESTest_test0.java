package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test0 extends CharSet_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        CharSet charSet0 = CharSet.ASCII_ALPHA_LOWER;
        String[] stringArray0 = new String[2];
        CharSet charSet1 = CharSet.getInstance(stringArray0);
        boolean boolean0 = charSet0.equals(charSet1);
        assertFalse(charSet1.equals((Object) charSet0));
        assertFalse(boolean0);
    }
}
