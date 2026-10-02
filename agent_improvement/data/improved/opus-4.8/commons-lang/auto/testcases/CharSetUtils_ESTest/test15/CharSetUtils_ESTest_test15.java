package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test15 extends CharSetUtils_ESTest_scaffolding {

    /**
     * When every element of the character set is empty (here, an all-null array),
     * {@link CharSetUtils#squeeze} has no characters to act on and returns the
     * input string unchanged.
     */
    @Test(timeout = 4000)
    public void squeezeWithEmptySetReturnsInputUnchanged() throws Throwable {
        String input = "Minimum abbreviation width with offset is %d";
        String[] emptyCharSet = new String[2];

        String result = CharSetUtils.squeeze(input, emptyCharSet);

        assertEquals(input, result);
    }
}
