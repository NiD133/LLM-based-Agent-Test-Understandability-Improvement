package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test02 extends StrMatcher_ESTest_scaffolding {

    /**
     * Calling charSetMatcher with an empty string should still return a
     * valid (non-null) matcher instance.
     */
    @Test(timeout = 4000)
    public void charSetMatcherWithEmptyStringReturnsMatcher() throws Throwable {
        StrMatcher emptyCharSetMatcher = StrMatcher.charSetMatcher("");

        assertNotNull(emptyCharSetMatcher);
    }
}
