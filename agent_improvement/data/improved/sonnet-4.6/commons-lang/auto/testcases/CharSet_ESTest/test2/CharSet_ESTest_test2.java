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
    public void test_equals_returnsFalse_whenComparedToNonCharSetObject() throws Throwable {
        CharSet lowerCaseAlpha = CharSet.ASCII_ALPHA_LOWER;
        Object nonCharSetObject = new Object();
        boolean isEqual = lowerCaseAlpha.equals(nonCharSetObject);
        assertFalse("CharSet should not be equal to a plain Object", isEqual);
    }
}
