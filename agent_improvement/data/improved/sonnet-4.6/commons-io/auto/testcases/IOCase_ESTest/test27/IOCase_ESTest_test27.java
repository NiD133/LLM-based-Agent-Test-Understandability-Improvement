package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test27 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test27_isCaseSensitiveReturnsFalseForInsensitiveCase() throws Throwable {
        // IOCase.INSENSITIVE represents case-insensitive comparison, so isCaseSensitive should return false
        IOCase insensitiveCase = IOCase.INSENSITIVE;
        boolean result = IOCase.isCaseSensitive(insensitiveCase);
        assertFalse(result);
    }
}
