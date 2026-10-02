package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test18 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that {@link IOCase#checkEndsWith(String, String)} returns false
     * when the string does not end with the given suffix.
     */
    @Test(timeout = 4000)
    public void checkEndsWith_returnsFalse_whenSuffixDoesNotMatch() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;

        boolean endsWith = systemCase.checkEndsWith("System", "z:=F{9w=V$70~Oy");

        assertFalse(endsWith);
    }
}
