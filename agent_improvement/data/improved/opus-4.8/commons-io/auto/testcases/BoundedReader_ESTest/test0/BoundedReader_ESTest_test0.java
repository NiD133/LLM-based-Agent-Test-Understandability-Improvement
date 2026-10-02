package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedReader_ESTest_test0 extends BoundedReader_ESTest_scaffolding {

    /**
     * Reading into a buffer should honor the character limit imposed by the
     * BoundedReader. Here the limit is 1, so a request to read 1 character
     * succeeds and returns the number of characters actually read (1).
     */
    @Test(timeout = 4000)
    public void readIntoBufferReturnsCharsReadWithinLimit() throws Throwable {
        final int maxCharsFromTarget = 1;
        final StringReader underlyingReader = new StringReader("KnLjCdWGnB@(}p3qC");
        final BoundedReader boundedReader = new BoundedReader(underlyingReader, maxCharsFromTarget);

        final char[] buffer = new char[14];
        final int offset = 1;
        final int charsToRead = 1;
        final int charsRead = boundedReader.read(buffer, offset, charsToRead);

        assertEquals(1, charsRead);
    }
}
