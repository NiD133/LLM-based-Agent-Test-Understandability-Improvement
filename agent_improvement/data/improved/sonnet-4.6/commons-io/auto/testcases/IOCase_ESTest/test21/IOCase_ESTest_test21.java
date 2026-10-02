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
    public void test_value_returnsFirstArgWhenNonNull_andInsensitiveCompareToEqualsIdenticalStrings() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        IOCase insensitiveCase = IOCase.INSENSITIVE;

        // value() returns the first argument when it is non-null, ignoring the default
        IOCase result = IOCase.value(insensitiveCase, systemCase);
        assertEquals(IOCase.INSENSITIVE, result);

        // INSENSITIVE.checkCompareTo returns 0 for identical strings
        result.checkCompareTo("org.apache.commons.io.Filena+eUtils", "org.apache.commons.io.Filena+eUtils");

        // The resolved value is INSENSITIVE, not the SYSTEM default passed in
        assertNotSame(systemCase, result);
    }
}
