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
public class NullInputStream_ESTest_test12 extends NullInputStream_ESTest_scaffolding {

    /**
     * Verifies that skip() throws EOFException when the stream is already at EOF
     * after a prior read() advanced position past the (negative) stream size.
     *
     * When size is negative, any read that advances position beyond size causes
     * position to clamp to size, leaving the stream immediately at EOF.
     * A subsequent skip() call detects position == size and throws EOFException
     * because throwEofException is set to true.
     */
    @Test(timeout = 4000)
    public void test_skipThrowsEOFException_whenStreamAlreadyAtEofAfterRead() throws Throwable {
        // Stream with negative size, mark not supported, throws EOFException at EOF
        long negativeSize = -4176L;
        NullInputStream stream = new NullInputStream(negativeSize, false, true);

        // Reading 1 byte advances position past the negative size boundary,
        // clamping position to size (EOF state).
        byte[] buffer = new byte[6];
        stream.read(buffer, 0, (int) (byte) 1);

        // Stream is now at EOF; skip() should throw EOFException immediately.
        try {
            stream.skip(-2268L);
            fail("Expecting exception: EOFException");
        } catch (EOFException e) {
            verifyException("org.apache.commons.io.input.NullInputStream", e);
        }
    }
}
