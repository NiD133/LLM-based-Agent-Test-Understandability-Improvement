package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test03 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that checkStartsWith returns false when both the string and
     * the start prefix are null, regardless of case sensitivity setting.
     */
    @Test(timeout = 4000)
    public void test_checkStartsWith_withNullStringAndNullStart_returnsFalse() throws Throwable {
        IOCase caseSensitive = IOCase.SENSITIVE;
        String nullString = null;
        String nullStart = null;

        boolean result = caseSensitive.checkStartsWith(nullString, nullStart);

        assertFalse(result);
    }
}
