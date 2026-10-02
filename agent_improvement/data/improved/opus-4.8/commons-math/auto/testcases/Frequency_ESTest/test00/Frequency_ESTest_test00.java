package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test00 extends Frequency_ESTest_scaffolding {

    /**
     * Two freshly created, empty Frequency instances should be considered equal,
     * since they hold identical (empty) data.
     */
    @Test(timeout = 4000)
    public void testEmptyFrequenciesAreEqual() throws Throwable {
        Frequency<Integer> emptyFrequency = new Frequency<Integer>();
        Frequency<Integer> anotherEmptyFrequency = new Frequency<Integer>();

        boolean frequenciesAreEqual = emptyFrequency.equals(anotherEmptyFrequency);

        assertTrue(frequenciesAreEqual);
    }
}
