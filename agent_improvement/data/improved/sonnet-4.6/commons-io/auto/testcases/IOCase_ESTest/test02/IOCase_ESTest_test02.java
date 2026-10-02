package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test02 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_checkStartsWith_returnsFalse_whenPrefixIsNull() throws Throwable {
        boolean startsWithNullPrefix = IOCase.SENSITIVE.checkStartsWith("", (String) null);
        assertFalse(startsWithNullPrefix);
    }
}
