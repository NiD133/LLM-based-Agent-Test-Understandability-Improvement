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

    /**
     * Once the number of characters consumed reaches the configured maximum,
     * {@link BoundedReader#read()} must return EOF regardless of the underlying
     * reader's state. Here {@code skip(511)} advances the internal char counter to
     * the 511-character limit, so the next {@code read()} is expected to return -1.
     */
    @Test(timeout = 4000)
    public void readReturnsEofOnceMaxCharsReached() throws Throwable {
        final int maxCharsFromTarget = 511;
        StringReader emptyReader = new StringReader("");
        BoundedReader boundedReader = new BoundedReader(emptyReader, maxCharsFromTarget);

        boundedReader.reset();
        boundedReader.skip(maxCharsFromTarget);
        boundedReader.mark(maxCharsFromTarget);

        int charOrEof = boundedReader.read();

        assertEquals(-1, charOrEof);
    }
}
