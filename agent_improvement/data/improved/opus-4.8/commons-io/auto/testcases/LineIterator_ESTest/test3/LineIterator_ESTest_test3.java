package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.StringReader;
import java.util.NoSuchElementException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LineIterator_ESTest_test3 extends LineIterator_ESTest_scaffolding {

    /**
     * Verifies that {@link LineIterator#hasNext()} is idempotent: when the
     * underlying reader has a line available, calling hasNext() repeatedly
     * keeps returning {@code true} without consuming the cached line.
     */
    @Test(timeout = 4000)
    public void hasNextReturnsTrueWhenLineAvailableAndIsRepeatable() throws Throwable {
        StringReader readerWithOneLine = new StringReader("Jo\"1* 1@z}r@|`5#o");
        LineIterator lineIterator = new LineIterator(readerWithOneLine);

        // First call reads and caches the line.
        lineIterator.hasNext();

        // A second call must still report the line as available.
        boolean hasNextAgain = lineIterator.hasNext();
        assertTrue(hasNextAgain);
    }
}
