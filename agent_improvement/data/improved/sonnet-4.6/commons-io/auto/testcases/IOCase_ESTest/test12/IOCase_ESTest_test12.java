package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test12 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_insensitive_checkEquals_returnsTrueForDifferentCase() throws Throwable {
        // IOCase.INSENSITIVE treats strings as equal regardless of case
        IOCase insensitiveCase = IOCase.INSENSITIVE;
        boolean areEqual = insensitiveCase.checkEquals("com6", "COM6");
        assertTrue(areEqual);
    }
}
