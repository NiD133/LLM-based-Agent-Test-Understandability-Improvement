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
public class NullInputStream_ESTest_test15 extends NullInputStream_ESTest_scaffolding {

    /**
     * A NullInputStream constructed with a negative size has no bytes to offer,
     * so available() returns 0. The single-long constructor also enables mark
     * support by default.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        // Negative size means the stream is treated as already past EOF.
        NullInputStream stream = new NullInputStream(-550L);

        int availableBytes = stream.available();

        assertEquals(0, availableBytes);
        assertTrue(stream.markSupported());
    }
}
