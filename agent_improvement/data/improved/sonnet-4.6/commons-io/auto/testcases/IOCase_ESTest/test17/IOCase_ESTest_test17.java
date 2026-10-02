package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test17 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_checkEndsWith_withNullEnd_returnsFalse() throws Throwable {
        // IOCase.SYSTEM uses the OS-determined case sensitivity
        IOCase systemCaseSensitivity = IOCase.SYSTEM;

        // checkEndsWith must return false when the 'end' argument is null
        boolean result = systemCaseSensitivity.checkEndsWith("%pe;MQhIB_m,ru(g&", (String) null);

        assertFalse("checkEndsWith should return false when the end argument is null", result);
    }
}
