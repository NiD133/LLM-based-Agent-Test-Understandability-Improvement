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
        IOCase defaultValue = IOCase.INSENSITIVE;
        IOCase primaryValue = IOCase.SENSITIVE;

        // value() returns primaryValue when it is non-null, ignoring defaultValue
        IOCase result = IOCase.value(primaryValue, defaultValue);
        assertEquals(IOCase.SENSITIVE, result);

        // A SENSITIVE IOCase reports case sensitivity as true
        boolean isSensitive = IOCase.isCaseSensitive(result);
        assertTrue(isSensitive);
    }
}
