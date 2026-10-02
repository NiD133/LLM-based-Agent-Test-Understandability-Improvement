package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test02 extends Months_ESTest_scaffolding {

    /**
     * Verifies that {@link Months#equals(Object)} is reflexive:
     * an instance must be equal to itself.
     */
    @Test(timeout = 4000)
    public void equalsIsReflexive() throws Throwable {
        Months oneMonth = Months.ONE;

        boolean isEqualToItself = oneMonth.equals(oneMonth);

        assertTrue("A Months value must be equal to itself", isEqualToItself);
    }
}
