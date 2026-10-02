package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test6 extends CharSet_ESTest_scaffolding {

    /**
     * Verifies that a CharSet built from a null String array can compute its
     * hash code without throwing. Passing a null array makes getInstance return
     * the shared EMPTY CharSet, whose hashCode is safe to invoke.
     */
    @Test(timeout = 4000)
    public void hashCodeOnCharSetFromNullArrayDoesNotThrow() throws Throwable {
        CharSet charSetFromNullArray = CharSet.getInstance((String[]) null);

        // hashCode should be callable on the resulting (empty) CharSet.
        charSetFromNullArray.hashCode();
    }
}
