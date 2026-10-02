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

    /**
     * Verifies that {@link IOCase#checkEndsWith(String, String)} returns false
     * when the "end" argument is null, regardless of the string being checked.
     */
    @Test(timeout = 4000)
    public void checkEndsWith_returnsFalse_whenEndIsNull() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;

        boolean endsWithNull = systemCase.checkEndsWith("%pe;MQhIB_m,ru(g&", (String) null);

        assertFalse("checkEndsWith should return false when the end argument is null", endsWithNull);
    }
}
