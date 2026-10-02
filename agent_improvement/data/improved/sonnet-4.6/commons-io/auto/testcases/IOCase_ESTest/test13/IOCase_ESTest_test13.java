package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test13 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        // Two completely different strings should never be equal under any case sensitivity rule
        boolean stringsAreEqual = systemCase.checkEquals("zmIl1;deJ|AOW", "^J# C/>cH!\"$");
        assertFalse(stringsAreEqual);
    }
}
