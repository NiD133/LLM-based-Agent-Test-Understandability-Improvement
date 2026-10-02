package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test21 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        IOCase explicitCase = IOCase.INSENSITIVE;

        IOCase resolvedCase = IOCase.value(explicitCase, systemCase);

        assertEquals(IOCase.INSENSITIVE, resolvedCase);
        resolvedCase.checkCompareTo("org.apache.commons.io.Filena+eUtils", "org.apache.commons.io.Filena+eUtils");
        assertNotSame(systemCase, resolvedCase);
    }
}
