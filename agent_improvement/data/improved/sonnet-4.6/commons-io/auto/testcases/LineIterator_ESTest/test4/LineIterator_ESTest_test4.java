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
public class LineIterator_ESTest_test4 extends LineIterator_ESTest_scaffolding {

    /**
     * Verifies that {@link LineIterator#next()} returns the single line of text
     * from a reader that contains no newline characters.
     * A reader holding a single-line string has exactly one element, and calling
     * next() on the iterator must return that entire string unchanged.
     */
    @Test(timeout = 4000)
    public void test_next_returnsSingleLineFromReader() throws Throwable {
        // Arrange: a reader whose content is one line with no newline
        StringReader singleLineReader = new StringReader("_CB");
        LineIterator lineIterator = new LineIterator(singleLineReader);

        // Act: retrieve the first (and only) line via next()
        String firstLine = lineIterator.next();

        // Assert: the returned line matches the full content of the reader
        assertEquals("_CB", firstLine);
    }
}
