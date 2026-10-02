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
public class NullReader_ESTest_test10 extends NullReader_ESTest_scaffolding {

    private static final long NEGATIVE_READER_SIZE = -2742L;
    private static final int BUFFER_LENGTH = 4;
    private static final int NEGATIVE_SKIP_AMOUNT = -2742;

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        NullReader readerThatThrowsAtEndOfFile = new NullReader(NEGATIVE_READER_SIZE, true, true);
        char[] readBuffer = new char[BUFFER_LENGTH];

        readerThatThrowsAtEndOfFile.read(readBuffer);

        try {
            readerThatThrowsAtEndOfFile.skip(NEGATIVE_SKIP_AMOUNT);
            fail("Expecting exception: EOFException");
        } catch (EOFException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.apache.commons.io.input.NullReader", e);
        }
    }
}
