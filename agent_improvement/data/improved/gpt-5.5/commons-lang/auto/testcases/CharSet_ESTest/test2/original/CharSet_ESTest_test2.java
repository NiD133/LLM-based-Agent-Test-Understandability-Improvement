package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test2 extends CharSet_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        CharSet charSet0 = CharSet.ASCII_ALPHA_LOWER;
        Object object0 = new Object();
        boolean boolean0 = charSet0.equals(object0);
        assertFalse(boolean0);
    }
}
