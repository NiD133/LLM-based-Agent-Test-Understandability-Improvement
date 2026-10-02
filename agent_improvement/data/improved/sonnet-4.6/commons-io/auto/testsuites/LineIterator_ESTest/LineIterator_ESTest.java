package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.StringReader;
import java.util.NoSuchElementException;
import org.apache.commons.io.LineIterator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LineIterator_ESTest extends LineIterator_ESTest_scaffolding {

    /**
     * When two LineIterators share the same underlying Reader and one is closed via
     * closeQuietly(), the other iterator's nextLine() should throw IllegalStateException
     * because the shared Reader stream is now closed.
     */
    @Test(timeout = 4000)
    public void test_nextLine_throwsIllegalStateException_whenSharedReaderClosedByAnotherIterator() throws Throwable {
        StringReader sharedReader = new StringReader("^jsd-+DG7(%74v");
        LineIterator firstIterator = new LineIterator(sharedReader);
        LineIterator secondIterator = new LineIterator(sharedReader);

        // Closing secondIterator closes the shared underlying Reader
        LineIterator.closeQuietly(secondIterator);

        try {
            firstIterator.nextLine();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // Reading from a closed stream should wrap IOException as IllegalStateException
            verifyException("org.apache.commons.io.LineIterator", e);
        }
    }

    /**
     * Calling nextLine() on an iterator over an empty reader should throw
     * NoSuchElementException, since there are no lines available.
     */
    @Test(timeout = 4000)
    public void test_nextLine_throwsNoSuchElementException_whenReaderIsEmpty() throws Throwable {
        StringReader emptyReader = new StringReader("");
        LineIterator iterator = new LineIterator(emptyReader);

        try {
            iterator.nextLine();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException("org.apache.commons.io.LineIterator", e);
        }
    }

    /**
     * hasNext() should return false consistently when the underlying reader is empty,
     * whether called once or multiple times.
     */
    @Test(timeout = 4000)
    public void test_hasNext_returnsFalse_whenReaderIsEmpty() throws Throwable {
        StringReader emptyReader = new StringReader("");
        LineIterator iterator = new LineIterator(emptyReader);

        boolean firstCall = iterator.hasNext();
        assertFalse(firstCall);

        boolean secondCall = iterator.hasNext();
        assertFalse(secondCall);
    }

    /**
     * hasNext() should return true when the reader contains content, and
     * calling it multiple times should yield the same result (idempotent).
     */
    @Test(timeout = 4000)
    public void test_hasNext_returnsTrue_whenReaderHasContent() throws Throwable {
        StringReader nonEmptyReader = new StringReader("Jo\"1* 1@z}r@|`5#o");
        LineIterator iterator = new LineIterator(nonEmptyReader);

        iterator.hasNext();
        boolean hasMoreLines = iterator.hasNext();

        assertTrue(hasMoreLines);
    }

    /**
     * next() should return the full line content when the reader contains a single line.
     */
    @Test(timeout = 4000)
    public void test_next_returnsLineContent_forSingleLinedReader() throws Throwable {
        StringReader singleLineReader = new StringReader("_CB");
        LineIterator iterator = new LineIterator(singleLineReader);

        String line = iterator.next();

        assertEquals("_CB", line);
    }

    /**
     * remove() is not supported by LineIterator and should always throw
     * UnsupportedOperationException regardless of iterator state.
     */
    @Test(timeout = 4000)
    public void test_remove_throwsUnsupportedOperationException() throws Throwable {
        StringReader reader = new StringReader("org.apache.commons.io.LineIterator");
        LineIterator iterator = new LineIterator(reader);

        try {
            iterator.remove();
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            verifyException("org.apache.commons.io.LineIterator", e);
        }
    }
}
