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
public class NullInputStream_ESTest_test03 extends NullInputStream_ESTest_scaffolding {

    /**
     * Verifies that skipping zero bytes returns 0 and that mark is supported
     * when the stream is constructed with markSupported=true.
     */
    @Test(timeout = 4000)
    public void test_skipZeroBytes_returnsZero_andMarkIsSupported() throws Throwable {
        // Stream of size 1 with mark support enabled and EOF exception enabled
        NullInputStream stream = new NullInputStream(1L, true, true);

        long bytesSkipped = stream.skip(0L);

        assertTrue("Stream should support mark when constructed with markSupported=true", stream.markSupported());
        assertEquals("Skipping 0 bytes should return 0", 0L, bytesSkipped);
    }
}
