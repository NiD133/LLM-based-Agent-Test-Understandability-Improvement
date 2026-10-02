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
public class NullInputStream_ESTest_test13 extends NullInputStream_ESTest_scaffolding {

    /**
     * Verifies that a newly constructed NullInputStream reports available bytes equal to its
     * configured size and supports mark/reset operations by default.
     */
    @Test(timeout = 4000)
    public void test_availableReturnsFullSizeAndMarkIsSupportedByDefault() throws Throwable {
        final long streamSize = 2480L;
        NullInputStream nullInputStream = new NullInputStream(streamSize);

        int availableBytes = nullInputStream.available();

        assertEquals(streamSize, availableBytes);
        assertTrue(nullInputStream.markSupported());
    }
}
