package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test15 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that case-insensitive equality check returns true for two
     * identical strings, since identical strings are equal regardless of
     * case sensitivity.
     */
    @Test(timeout = 4000)
    public void checkEquals_withIdenticalStrings_returnsTrue() throws Throwable {
        IOCase insensitive = IOCase.INSENSITIVE;

        boolean stringsAreEqual = insensitive.checkEquals("Hh}^", "Hh}^");

        assertTrue(stringsAreEqual);
    }
}
