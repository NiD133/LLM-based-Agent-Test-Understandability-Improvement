package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test02 extends CharSetUtils_ESTest_scaffolding {

    /**
     * Squeezing "..." against a set that contains the '.' character collapses
     * the three repeated dots down to a single ".". The set is supplied as a
     * String[] whose only non-null element is "..." (a null element is ignored).
     */
    @Test(timeout = 4000)
    public void testSqueezeCollapsesRepeatedCharactersInSet() throws Throwable {
        String input = "...";
        String[] characterSet = new String[2];
        characterSet[1] = "...";

        String squeezed = CharSetUtils.squeeze(input, characterSet);

        assertEquals(".", squeezed);
    }
}
