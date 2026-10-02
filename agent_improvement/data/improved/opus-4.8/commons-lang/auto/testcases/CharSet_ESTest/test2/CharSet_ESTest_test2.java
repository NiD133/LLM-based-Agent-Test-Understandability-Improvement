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

    /**
     * A CharSet is never equal to an object that is not a CharSet:
     * equals() should return false when compared against a plain Object.
     */
    @Test(timeout = 4000)
    public void equals_returnsFalse_forNonCharSetObject() throws Throwable {
        CharSet asciiLowerLetters = CharSet.ASCII_ALPHA_LOWER;
        Object nonCharSet = new Object();

        boolean isEqual = asciiLowerLetters.equals(nonCharSet);

        assertFalse(isEqual);
    }
}
