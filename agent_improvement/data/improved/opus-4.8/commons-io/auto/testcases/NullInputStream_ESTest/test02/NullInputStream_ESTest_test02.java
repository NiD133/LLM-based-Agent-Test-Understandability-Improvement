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
public class NullInputStream_ESTest_test02 extends NullInputStream_ESTest_scaffolding {

    /**
     * Skipping past the end of the emulated stream clamps the position to the
     * stream size and reports only the number of bytes actually skipped.
     *
     * <p>Here the emulated size is negative (-2268) and starts at position 0,
     * so the requested skip of 2163 bytes overshoots the end. {@code skip}
     * therefore caps the position at the size (-2268) and returns the clamped
     * skip count, which is the size value (-2268) rather than the 2163 requested.</p>
     */
    @Test(timeout = 4000)
    public void skipPastEndClampsPositionToStreamSize() throws Throwable {
        final long emulatedSize = -2268L;
        final long bytesToSkip = 2163L;
        NullInputStream nullInputStream = new NullInputStream(emulatedSize);

        long bytesSkipped = nullInputStream.skip(bytesToSkip);

        assertEquals("position should be clamped to the emulated size", emulatedSize, nullInputStream.getPosition());
        assertEquals("skip should report the clamped byte count", emulatedSize, bytesSkipped);
    }
}
