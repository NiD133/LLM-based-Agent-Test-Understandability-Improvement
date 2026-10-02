package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test08 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that checkIndexOf returns -1 when the search string is null,
     * regardless of case sensitivity mode or the start index supplied.
     */
    @Test(timeout = 4000)
    public void test_checkIndexOf_returnsMinusOne_whenSearchStringIsNull() throws Throwable {
        IOCase systemCaseSensitivity = IOCase.SYSTEM;
        String nullSearchString = null;

        int result = systemCaseSensitivity.checkIndexOf("System", 24, nullSearchString);

        assertEquals(-1, result);
    }
}
