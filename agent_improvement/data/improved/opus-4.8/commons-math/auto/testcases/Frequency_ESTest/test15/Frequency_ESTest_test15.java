package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test15 extends Frequency_ESTest_scaffolding {

    /**
     * Verifies that calling {@link Frequency#clear()} on a newly created,
     * empty Frequency completes without throwing an exception.
     */
    @Test(timeout = 4000)
    public void clearOnEmptyFrequencySucceeds() throws Throwable {
        Frequency<Integer> emptyFrequency = new Frequency<Integer>();

        emptyFrequency.clear();
    }
}
