package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test02 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that {@link Minutes#equals(Object)} is reflexive:
     * a {@code Minutes} instance is always equal to itself.
     */
    @Test(timeout = 4000)
    public void equals_isReflexive_forSameInstance() throws Throwable {
        Minutes zeroMinutes = Minutes.ZERO;

        boolean isEqualToItself = zeroMinutes.equals(zeroMinutes);

        assertTrue("A Minutes instance must be equal to itself", isEqualToItself);
    }
}
