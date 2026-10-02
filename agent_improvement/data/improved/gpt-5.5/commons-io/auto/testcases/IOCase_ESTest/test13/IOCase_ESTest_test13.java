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
        IOCase systemCaseSensitivity = IOCase.SYSTEM;
        String firstValue = "zmIl1;deJ|AOW";
        String secondValue = "^J# C/>cH!\"$";

        boolean valuesAreEqual = systemCaseSensitivity.checkEquals(firstValue, secondValue);

        assertFalse(valuesAreEqual);
    }
}
