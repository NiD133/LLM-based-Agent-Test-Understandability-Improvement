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
public class NullReader_ESTest_test05 extends NullReader_ESTest_scaffolding {

    /**
     * Verifies that reading into a char array advances the reader's position
     * by the number of characters actually read.
     *
     * A NullReader of size 1017 has more than enough capacity to satisfy
     * a read of 8 characters, so the full array should be filled and the
     * internal position should move to 8.
     */
    @Test(timeout = 4000)
    public void test05_readIntoArray_advancesPositionByCharsRead() throws Throwable {
        final long readerSize = 1017L;
        final int bufferSize = 8;

        NullReader reader = new NullReader(readerSize);
        char[] buffer = new char[bufferSize];

        int charsRead = reader.read(buffer);

        assertEquals("All buffer slots should be filled", bufferSize, charsRead);
        assertEquals("Reader position should equal the number of chars read", (long) bufferSize, reader.getPosition());
    }
}
