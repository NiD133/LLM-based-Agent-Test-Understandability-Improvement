package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test22 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that {@link IOCase#value(IOCase, IOCase)} falls back to the
     * supplied default when the primary value is null.
     */
    @Test(timeout = 4000)
    public void valueWithNullPrimaryReturnsDefault() throws Throwable {
        IOCase defaultValue = IOCase.SYSTEM;

        IOCase result = IOCase.value((IOCase) null, defaultValue);

        assertEquals(IOCase.SYSTEM, result);
    }
}
