package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test02 extends Days_ESTest_scaffolding {

    /**
     * Verifies that {@link Days#equals(Object)} is reflexive:
     * a {@code Days} instance is always equal to itself.
     */
    @Test(timeout = 4000)
    public void equals_isReflexive_returnsTrueForSameInstance() throws Throwable {
        Days oneDay = Days.ONE;

        boolean isEqualToItself = oneDay.equals(oneDay);

        assertTrue("Days.ONE should be equal to itself", isEqualToItself);
    }
}
