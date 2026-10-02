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
    public void test17_checkEndsWith_nullSuffix_returnsFalse() throws Throwable {
        // checkEndsWith must return false when the suffix argument is null,
        // regardless of the case-sensitivity mode or the value of the main string.
        IOCase systemCase = IOCase.SYSTEM;
        String anyString = "%pe;MQhIB_m,ru(g&";
        boolean result = systemCase.checkEndsWith(anyString, (String) null);
        assertFalse(result);
    }
}
