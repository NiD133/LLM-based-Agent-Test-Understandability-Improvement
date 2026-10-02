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
public class LineIterator_ESTest_test0 extends LineIterator_ESTest_scaffolding {

    /**
     * When two LineIterators share the same underlying Reader, closing one of
     * them closes the shared Reader. Reading from the other iterator afterwards
     * then fails: its internal readLine() raises an IOException ("Stream closed")
     * which LineIterator wraps and rethrows as an IllegalStateException.
     */
    @Test(timeout = 4000)
    public void nextLineThrowsIllegalStateExceptionWhenSharedReaderIsClosed() throws Throwable {
        StringReader sharedReader = new StringReader("^jsd-+DG7(%74v");
        LineIterator iteratorUnderTest = new LineIterator(sharedReader);
        LineIterator otherIteratorOnSameReader = new LineIterator(sharedReader);

        // Closing this iterator also closes the Reader shared with iteratorUnderTest.
        LineIterator.closeQuietly(otherIteratorOnSameReader);

        try {
            iteratorUnderTest.nextLine();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // Caused by java.io.IOException: Stream closed
            verifyException("org.apache.commons.io.LineIterator", e);
        }
    }
}
