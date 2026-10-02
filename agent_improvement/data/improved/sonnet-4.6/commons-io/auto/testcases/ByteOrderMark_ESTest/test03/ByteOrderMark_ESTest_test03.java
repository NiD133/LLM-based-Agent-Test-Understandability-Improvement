package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test03 extends ByteOrderMark_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_matches_returnsfalse_whenInputArrayIsNull() throws Throwable {
        // UTF_32BE.matches(null) must return false; a null input cannot start with any BOM bytes.
        boolean result = ByteOrderMark.UTF_32BE.matches((int[]) null);
        assertFalse("matches(null) should return false for any BOM", result);
    }
}
