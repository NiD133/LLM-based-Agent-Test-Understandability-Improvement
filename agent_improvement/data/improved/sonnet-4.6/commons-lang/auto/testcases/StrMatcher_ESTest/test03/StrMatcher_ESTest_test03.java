package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test03 extends StrMatcher_ESTest_scaffolding {

    // charSetMatcher treats a null char array as empty and should return the no-op NONE_MATCHER, not throw
    @Test(timeout = 4000)
    public void test03_charSetMatcher_withNullArray_returnsNonNullNoOpMatcher() throws Throwable {
        StrMatcher noOpMatcher = StrMatcher.charSetMatcher((char[]) null);
        assertNotNull(noOpMatcher);
    }
}
