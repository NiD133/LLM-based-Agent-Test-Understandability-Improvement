package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.EOFException;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullReader_ESTest_test00 extends NullReader_ESTest_scaffolding {

    /**
     * Verifies that skipping zero characters on a NullReader returns 0,
     * and that mark/reset support is enabled by default.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        NullReader reader = new NullReader(1017L);

        long charsSkipped = reader.skip(0L);

        assertEquals("Skipping 0 characters should return 0", 0L, charsSkipped);
        assertTrue("NullReader should support mark by default", reader.markSupported());
    }
}
