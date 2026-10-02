package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test8 extends CharSet_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_asciiAlphaLowerToString_returnsLowercaseRangeNotation() throws Throwable {
        // ASCII_ALPHA_LOWER is the predefined CharSet for lowercase letters 'a' through 'z'.
        // Its toString() reflects the single CharRange [a-z] stored internally.
        String representation = CharSet.ASCII_ALPHA_LOWER.toString();
        assertEquals("[a-z]", representation);
    }
}
