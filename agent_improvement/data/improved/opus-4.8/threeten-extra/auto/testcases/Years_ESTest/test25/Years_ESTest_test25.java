package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test25 extends Years_ESTest_scaffolding {

    /**
     * Comparing a Years instance to itself should report equality,
     * so compareTo must return 0.
     */
    @Test(timeout = 4000)
    public void compareToSameInstanceReturnsZero() throws Throwable {
        Years oneYear = Years.ONE;

        int comparisonResult = oneYear.compareTo(oneYear);

        assertEquals(0, comparisonResult);
    }
}
