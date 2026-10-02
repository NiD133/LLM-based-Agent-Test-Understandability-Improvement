package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test16 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16_checkEndsWith_returnsFalse_whenSuffixIsLongerThanString() throws Throwable {
        // "System" (6 chars) cannot end with a suffix that is longer than itself
        IOCase caseInsensitive = IOCase.INSENSITIVE;
        boolean result = caseInsensitive.checkEndsWith("System", "z:=F{9w=V$70~Oy");
        assertFalse(result);
    }
}
