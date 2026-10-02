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
public class NullInputStream_ESTest_test14 extends NullInputStream_ESTest_scaffolding {

    // A stream size that exceeds Integer.MAX_VALUE, so available() must cap its return value
    private static final long SIZE_EXCEEDING_INT_MAX = Integer.MAX_VALUE + 1494L;

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        NullInputStream nullInputStream0 = new NullInputStream(SIZE_EXCEEDING_INT_MAX);

        // available() caps at Integer.MAX_VALUE when remaining bytes exceed it
        int availableBytes = nullInputStream0.available();
        assertEquals(Integer.MAX_VALUE, availableBytes);

        // mark is supported by default
        assertTrue(nullInputStream0.markSupported());
    }
}
