package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test04 extends CharSetUtils_ESTest_scaffolding {

    /**
     * keep() returns only the characters of the input string that also appear
     * in the given set. Here the single character "E" is not part of the set
     * "!f0C7\"CJoqlK", so nothing is kept and the result is the empty string.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        String[] charactersToKeep = new String[9];
        charactersToKeep[3] = "!f0C7\"CJoqlK";

        String kept = CharSetUtils.keep("E", charactersToKeep);

        assertEquals("", kept);
    }
}
