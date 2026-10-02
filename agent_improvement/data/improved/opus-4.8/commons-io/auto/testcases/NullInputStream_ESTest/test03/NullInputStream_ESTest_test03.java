package org.apache.commons.io.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test03 extends NullInputStream_ESTest_scaffolding {

    /**
     * Skipping zero bytes should always return zero, regardless of stream size,
     * and should leave the stream's mark support unaffected.
     */
    @Test(timeout = 4000)
    public void skipZeroBytesReturnsZeroAndMarkRemainsSupported() throws Throwable {
        long emulatedSize = 1L;
        boolean markSupported = true;
        boolean throwEofException = true;
        NullInputStream nullInputStream = new NullInputStream(emulatedSize, markSupported, throwEofException);

        long bytesSkipped = nullInputStream.skip(0L);

        assertEquals(0L, bytesSkipped);
        assertTrue(nullInputStream.markSupported());
    }
}
