package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test25 extends Years_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void compareToSelf_shouldReturnZero() throws Throwable {
        // A Years instance compared to itself must equal zero (reflexive ordering)
        Years oneYear = Years.ONE;
        int comparisonResult = oneYear.compareTo(oneYear);
        assertEquals(0, comparisonResult);
    }
}
