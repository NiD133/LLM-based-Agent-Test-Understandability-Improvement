package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test02 extends Months_ESTest_scaffolding {

    /**
     * equals() must be reflexive: an instance is always equal to itself.
     */
    @Test(timeout = 4000)
    public void equalsReturnsTrueWhenComparedToItself() throws Throwable {
        Months oneMonth = Months.ONE;

        boolean isEqualToItself = oneMonth.equals(oneMonth);

        assertTrue(isEqualToItself);
    }
}
