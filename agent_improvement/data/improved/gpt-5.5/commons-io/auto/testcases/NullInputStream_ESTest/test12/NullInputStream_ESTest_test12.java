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

    private static final long NEGATIVE_STREAM_SIZE = -4176L;
    private static final boolean MARK_NOT_SUPPORTED = false;
    private static final boolean THROW_EOF_EXCEPTION = true;
    private static final int BUFFER_SIZE = 6;
    private static final int READ_OFFSET = 0;
    private static final int READ_LENGTH = 1;
    private static final long NEGATIVE_SKIP_LENGTH = -2268L;

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        NullInputStream inputStream = new NullInputStream(NEGATIVE_STREAM_SIZE, MARK_NOT_SUPPORTED, THROW_EOF_EXCEPTION);
        byte[] buffer = new byte[BUFFER_SIZE];

        inputStream.read(buffer, READ_OFFSET, READ_LENGTH);

        try {
            inputStream.skip(NEGATIVE_SKIP_LENGTH);
            fail("Expecting exception: EOFException");
        } catch (EOFException e) {
            verifyException("org.apache.commons.io.input.NullInputStream", e);
        }
    }
}
