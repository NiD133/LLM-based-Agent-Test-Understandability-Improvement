package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test12 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that the INSENSITIVE case rule treats two strings that differ
     * only in letter case ("com6" vs. "COM6") as equal.
     */
    @Test(timeout = 4000)
    public void checkEquals_caseInsensitive_ignoresLetterCase() throws Throwable {
        IOCase caseInsensitive = IOCase.INSENSITIVE;

        boolean stringsAreEqual = caseInsensitive.checkEquals("com6", "COM6");

        assertTrue("INSENSITIVE should treat 'com6' and 'COM6' as equal", stringsAreEqual);
    }
}
