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
    public void test18() throws Throwable {
        final IOCase systemCaseSensitivity = IOCase.SYSTEM;
        final String text = "System";
        final String nonMatchingSuffix = "z:=F{9w=V$70~Oy";

        final boolean endsWithSuffix = systemCaseSensitivity.checkEndsWith(text, nonMatchingSuffix);

        assertFalse(endsWithSuffix);
    }
}
