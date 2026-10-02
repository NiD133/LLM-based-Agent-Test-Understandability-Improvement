package org.apache.commons.io.input;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.io.EOFException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test12 extends NullInputStream_ESTest_scaffolding {

    /**
     * Verifies that {@link NullInputStream#skip(long)} throws an {@link EOFException}
     * once the stream is exhausted, when the stream is configured to signal
     * end-of-file via an exception.
     *
     * <p>The stream is built with a negative emulated size, so any read pushes the
     * position past the (negative) size and clamps it to the end of the stream.
     * The subsequent {@code skip} therefore starts at end-of-file and triggers the
     * exception.</p>
     */
    @Test(timeout = 4000)
    public void skipAtEndOfFileThrowsEofExceptionWhenConfiguredToThrow() throws Throwable {
        // Emulated size is negative; mark unsupported; throw EOFException at end-of-file.
        final long emulatedSize = -4176L;
        final NullInputStream stream = new NullInputStream(emulatedSize, false, true);

        // First read clamps the position to the end of the (negative-sized) stream.
        final byte[] buffer = new byte[6];
        stream.read(buffer, 0, 1);

        // Skipping now begins at end-of-file, which must raise an EOFException.
        try {
            stream.skip(-2268L);
            fail("Expecting exception: EOFException");
        } catch (EOFException e) {
            // Raised by NullInputStream.handleEof().
            verifyException("org.apache.commons.io.input.NullInputStream", e);
        }
    }
}
