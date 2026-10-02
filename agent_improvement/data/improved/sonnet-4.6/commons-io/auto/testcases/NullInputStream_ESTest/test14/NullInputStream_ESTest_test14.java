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

    /**
     * Verifies that available() is capped at Integer.MAX_VALUE when the stream size
     * exceeds that limit, and that mark/reset is supported by default.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // Stream size exceeds Integer.MAX_VALUE, so available() must cap at Integer.MAX_VALUE
        long sizeExceedingIntMax = 2147485141L;
        NullInputStream nullInputStream0 = new NullInputStream(sizeExceedingIntMax);

        int availableBytes = nullInputStream0.available();
        assertEquals(Integer.MAX_VALUE, availableBytes);

        assertTrue(nullInputStream0.markSupported());
    }
}
