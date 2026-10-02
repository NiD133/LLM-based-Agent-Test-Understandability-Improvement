package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test8 extends CharSet_ESTest_scaffolding {

    /**
     * Verifies that the predefined lower-case ASCII alphabet CharSet renders
     * its single "a-z" range as the string "[a-z]".
     */
    @Test(timeout = 4000)
    public void toString_onAsciiAlphaLowerCharSet_returnsLowerCaseRange() throws Throwable {
        CharSet lowerCaseAlpha = CharSet.ASCII_ALPHA_LOWER;

        String rendered = lowerCaseAlpha.toString();

        assertEquals("[a-z]", rendered);
    }
}
