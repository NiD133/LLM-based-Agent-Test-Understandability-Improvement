package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test14 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_checkEquals_returnsFalse_whenFirstStringIsNull() throws Throwable {
        // IOCase.SYSTEM uses the OS-determined case sensitivity rule
        IOCase systemCase = IOCase.SYSTEM;

        // checkEquals should return false when the first argument is null,
        // regardless of the second argument's value
        boolean result = systemCase.checkEquals((String) null, "phuL");

        assertFalse("checkEquals must return false when str1 is null", result);
    }
}
