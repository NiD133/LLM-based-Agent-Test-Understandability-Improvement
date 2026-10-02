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

    /**
     * Verifies that read() returns EOF when the underlying source is empty,
     * even after reset(), skip(), and mark() have been called in sequence.
     *
     * Sequence:
     *  1. reset() sets charsRead to -1 (the initial markedAt value).
     *  2. skip(511) advances charsRead to 510.
     *  3. mark(511) records the mark position at 510 and sets readAheadLimit to 1.
     *  4. read() increments charsRead to 511 and delegates to the empty StringReader,
     *     which returns EOF (-1).
     */
    @Test(timeout = 4000)
    public void test_readReturnsEOF_whenSourceIsEmptyAfterResetSkipAndMark() throws Throwable {
        // Arrange: a BoundedReader wrapping an empty source with a 511-character limit
        StringReader emptySource = new StringReader("");
        BoundedReader boundedReader = new BoundedReader(emptySource, MAX_CHARS);

        // Act: manipulate reader state before reading
        boundedReader.reset();          // charsRead becomes -1 (initial markedAt)
        boundedReader.skip(MAX_CHARS);  // charsRead advances to 510
        boundedReader.mark(MAX_CHARS);  // mark position recorded; readAheadLimit set to 1

        int result = boundedReader.read();

        // Assert: the empty source yields EOF
        assertEquals(-1, result);
    }
}
