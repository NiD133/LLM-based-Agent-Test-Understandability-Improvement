package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test24 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_isCaseSensitive_withNullIOCase_returnsFalse() throws Throwable {
        // IOCase.isCaseSensitive is documented as null-safe: a null argument must return false
        boolean isSensitive = IOCase.isCaseSensitive((IOCase) null);
        assertFalse("isCaseSensitive(null) should return false, not throw or return true", isSensitive);
    }
}
