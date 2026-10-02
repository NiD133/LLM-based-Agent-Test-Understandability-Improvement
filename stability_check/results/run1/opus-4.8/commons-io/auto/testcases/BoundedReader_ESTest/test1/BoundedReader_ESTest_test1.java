package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedReader_ESTest_test1 extends BoundedReader_ESTest_scaffolding {

    private static final int MAX_CHARS = 511;
    private static final int EOF = -1;

    /**
     * Once the number of characters skipped reaches the configured maximum,
     * the BoundedReader must report end-of-file on the next read, regardless
     * of the underlying reader's state.
     */
    @Test(timeout = 4000)
    public void readReturnsEofAfterSkippingUpToMaxChars() throws Throwable {
        StringReader underlyingReader = new StringReader("");
        BoundedReader boundedReader = new BoundedReader(underlyingReader, MAX_CHARS);

        boundedReader.reset();
        boundedReader.skip(MAX_CHARS);
        boundedReader.mark(MAX_CHARS);

        int charOrEof = boundedReader.read();

        assertEquals(EOF, charOrEof);
    }
}
