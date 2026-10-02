package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test1 extends CharSet_ESTest_scaffolding {

    /**
     * Verifies that {@link CharSet#equals(Object)} is reflexive: a CharSet
     * always equals itself.
     */
    @Test(timeout = 4000)
    public void equalsIsReflexive() throws Throwable {
        // Build a CharSet from a definition array whose single element is null
        // (a null definition contributes no character ranges).
        String[] definitions = new String[1];
        CharSet charSet = CharSet.getInstance(definitions);

        boolean equalsItself = charSet.equals(charSet);

        assertTrue("A CharSet must be equal to itself", equalsItself);
    }
}
