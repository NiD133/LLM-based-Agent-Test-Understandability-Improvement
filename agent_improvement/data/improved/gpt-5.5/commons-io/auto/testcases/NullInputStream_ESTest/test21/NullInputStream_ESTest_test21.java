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

    private static final long NEGATIVE_STREAM_SIZE = -46L;
    private static final int EOF = -1;

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        NullInputStream negativeSizedStream = new NullInputStream(NEGATIVE_STREAM_SIZE);
        byte[] oneByteBuffer = new byte[1];

        negativeSizedStream.read(oneByteBuffer);
        int nextByte = negativeSizedStream.read();

        assertEquals(NEGATIVE_STREAM_SIZE, negativeSizedStream.getPosition());
        assertEquals(EOF, nextByte);
    }
}
