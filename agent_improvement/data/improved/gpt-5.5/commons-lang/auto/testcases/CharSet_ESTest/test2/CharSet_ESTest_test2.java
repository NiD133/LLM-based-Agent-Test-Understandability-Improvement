package org.apache.commons.lang3;

import static org.junit.Assert.assertFalse;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test2 extends CharSet_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        CharSet lowercaseAsciiLetters = CharSet.ASCII_ALPHA_LOWER;
        Object nonCharSetObject = new Object();

        boolean equalsNonCharSetObject = lowercaseAsciiLetters.equals(nonCharSetObject);

        assertFalse(equalsNonCharSetObject);
    }
}
