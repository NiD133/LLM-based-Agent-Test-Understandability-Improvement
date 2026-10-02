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

    // Stream size exceeds Integer.MAX_VALUE to verify available() caps its return value at Integer.MAX_VALUE
    private static final long SIZE_EXCEEDING_INT_MAX = 2147485141L;

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        NullInputStream stream = new NullInputStream(SIZE_EXCEEDING_INT_MAX);

        int availableBytes = stream.available();

        assertEquals(Integer.MAX_VALUE, availableBytes);
        assertTrue(stream.markSupported());
    }
}
