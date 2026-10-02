package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test20 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that {@link IOCase#checkCompareTo(String, String)} returns 0
     * when both arguments are the same string, mirroring the contract of
     * {@link String#compareTo(String)} for equal values.
     */
    @Test(timeout = 4000)
    public void checkCompareToReturnsZeroForEqualStrings() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        String fileName = "org.apache.commons.io.Filena+eUtils";

        int comparisonResult = systemCase.checkCompareTo(fileName, fileName);

        assertEquals(0, comparisonResult);
    }
}
