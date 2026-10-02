package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test5 extends CharSet_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        String[] characterSetDefinitions = new String[1];
        characterSetDefinitions[0] = "A-Xa-z";

        CharSet charSet = CharSet.getInstance(characterSetDefinitions);

        assertNotNull(charSet);
    }
}
