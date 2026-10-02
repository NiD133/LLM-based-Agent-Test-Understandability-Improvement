package org.threeten.extra;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test02 extends Months_ESTest_scaffolding {

    /**
     * Verifies that {@link Months#equals(Object)} is reflexive:
     * an instance is always equal to itself.
     */
    @Test(timeout = 4000)
    public void oneMonthEqualsItself() throws Throwable {
        Months oneMonth = Months.ONE;

        boolean isEqualToItself = oneMonth.equals(oneMonth);

        assertTrue("A Months instance should be equal to itself", isEqualToItself);
    }
}
