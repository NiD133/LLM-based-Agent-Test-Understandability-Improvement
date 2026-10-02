package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test21 extends NullInputStream_ESTest_scaffolding {

    /**
     * Verifies that once the (negative) emulated size has been reached, a single-byte
     * {@code read()} reports end-of-file by returning -1 and leaves the position unchanged.
     *
     * <p>The stream is constructed with a negative emulated size of -46. The first call,
     * {@code read(byte[])} with a 1-byte buffer, advances the position straight to the
     * emulated size (-46) because reading clamps the position to the stream size. With the
     * position now equal to the size, the subsequent {@code read()} is at end-of-file and
     * returns -1 without moving the position.</p>
     */
    @Test(timeout = 4000)
    public void readAtNegativeSizeReturnsEofAndKeepsPosition() throws Throwable {
        final long emulatedSize = -46L;
        NullInputStream nullInputStream = new NullInputStream(emulatedSize);

        // Reading into a buffer clamps the position to the emulated size.
        byte[] buffer = new byte[1];
        nullInputStream.read(buffer);

        // The position is now at the emulated size, so the next read hits end-of-file.
        int eofResult = nullInputStream.read();

        assertEquals(emulatedSize, nullInputStream.getPosition());
        assertEquals(-1, eofResult);
    }
}
