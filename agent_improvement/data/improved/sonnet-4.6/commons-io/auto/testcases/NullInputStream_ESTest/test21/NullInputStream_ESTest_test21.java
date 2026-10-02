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
public class NullInputStream_ESTest_test21 extends NullInputStream_ESTest_scaffolding {

    // A negative size places the stream's logical end before position 0,
    // so any read causes the position to clamp to this negative boundary.
    private static final long NEGATIVE_STREAM_SIZE = -46L;

    @Test(timeout = 4000)
    public void test21_negativeSize_positionClampsToSizeAndSubsequentReadReturnsEof() throws Throwable {
        NullInputStream stream = new NullInputStream(NEGATIVE_STREAM_SIZE);
        byte[] buffer = new byte[1];

        // Reading into the buffer advances position past the negative size boundary,
        // causing it to clamp back to NEGATIVE_STREAM_SIZE.
        stream.read(buffer);

        // With position == size, the stream reports EOF (-1) on the next read.
        int readResult = stream.read();

        assertEquals(NEGATIVE_STREAM_SIZE, stream.getPosition());
        assertEquals(-1, readResult);
    }
}
