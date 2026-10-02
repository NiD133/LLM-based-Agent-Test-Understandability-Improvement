package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test01 extends Frequency_ESTest_scaffolding {

    /**
     * A Frequency instance must be equal to itself (reflexivity of equals()).
     */
    @Test(timeout = 4000)
    public void equalsIsReflexive() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();

        boolean isEqualToItself = frequency.equals(frequency);

        assertTrue("A Frequency instance should be equal to itself", isEqualToItself);
    }
}
