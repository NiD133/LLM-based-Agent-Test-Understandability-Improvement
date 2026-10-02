package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test7 extends CharSet_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test7() throws Throwable {
        String[] nullOnlyDefinitions = new String[1];
        CharSet charSet = CharSet.getInstance(nullOnlyDefinitions);

        boolean containsLetterV = charSet.contains('v');

        assertFalse(containsLetterV);
    }
}
