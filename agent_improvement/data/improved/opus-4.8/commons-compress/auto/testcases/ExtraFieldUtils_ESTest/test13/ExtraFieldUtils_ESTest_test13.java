package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ExtraFieldUtils_ESTest_test13 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * Verifies that the SKIP action exposes its documented key value (SKIP_KEY = 1).
     */
    @Test(timeout = 4000)
    public void skipActionReturnsSkipKey() throws Throwable {
        ExtraFieldUtils.UnparseableExtraField skipAction = ExtraFieldUtils.UnparseableExtraField.SKIP;

        int key = skipAction.getKey();

        assertEquals(ExtraFieldUtils.UnparseableExtraField.SKIP_KEY, key);
        assertEquals(1, key);
    }
}
