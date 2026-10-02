package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test18 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_checkEndsWith_withNonMatchingSuffix_returnsFalse() throws Throwable {
        // IOCase.SYSTEM uses the OS-determined case sensitivity for comparisons
        IOCase systemCaseSensitivity = IOCase.SYSTEM;

        // "System" does not end with this unrelated string, so the result must be false
        boolean endsWithResult = systemCaseSensitivity.checkEndsWith("System", "z:=F{9w=V$70~Oy");

        assertFalse(endsWithResult);
    }
}
