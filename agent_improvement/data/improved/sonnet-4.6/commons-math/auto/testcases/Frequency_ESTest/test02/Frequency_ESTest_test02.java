package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test02 extends Frequency_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();
        Object nonFrequencyObject = new Object();
        assertFalse(frequency.equals(nonFrequencyObject));
    }
}
