package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test11 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // INSENSITIVE performs case-insensitive equality; two distinct strings should not be equal
        assertFalse(IOCase.INSENSITIVE.checkEquals("%Te;M@?B_m,ru(g&", "$VALUES"));
    }
}
