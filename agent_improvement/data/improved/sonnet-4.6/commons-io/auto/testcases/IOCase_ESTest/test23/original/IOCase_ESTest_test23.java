package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test23 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        IOCase iOCase0 = IOCase.INSENSITIVE;
        IOCase iOCase1 = IOCase.SENSITIVE;
        IOCase iOCase2 = IOCase.value(iOCase1, iOCase0);
        assertEquals(IOCase.SENSITIVE, iOCase2);
        boolean boolean0 = IOCase.isCaseSensitive(iOCase2);
        assertTrue(boolean0);
    }
}
