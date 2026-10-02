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
    public void test12() throws Throwable {
        IOCase caseInsensitiveComparison = IOCase.INSENSITIVE;

        boolean matchesIgnoringCase = caseInsensitiveComparison.checkEquals("com6", "COM6");

        assertTrue("INSENSITIVE comparisons should ignore character case.", matchesIgnoringCase);
    }
}
