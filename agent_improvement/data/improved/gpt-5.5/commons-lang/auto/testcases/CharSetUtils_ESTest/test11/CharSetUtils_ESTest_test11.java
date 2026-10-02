package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test11 extends CharSetUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        final String input = "!NIzU+h g./^6";
        final String[] characterSets = new String[4];
        characterSets[0] = input;

        final int matchingCharacterCount = CharSetUtils.count(input, characterSets);

        assertEquals(12, matchingCharacterCount);
    }
}
