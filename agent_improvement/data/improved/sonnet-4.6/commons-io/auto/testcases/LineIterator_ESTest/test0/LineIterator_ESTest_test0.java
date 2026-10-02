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
     * Verifies that calling nextLine() throws IllegalStateException when the
     * underlying reader has been closed by closing a different LineIterator
     * that shares the same StringReader.
     *
     * Two LineIterators are created over the same StringReader. Closing the
     * second iterator via closeQuietly() closes the shared reader. A
     * subsequent call to nextLine() on the first iterator must propagate the
     * resulting IOException as an IllegalStateException.
     */
    @Test(timeout = 4000)
    public void test_nextLine_throwsIllegalStateException_whenSharedReaderIsClosed() throws Throwable {
        StringReader sharedReader = new StringReader("^jsd-+DG7(%74v");

        LineIterator primaryIterator = new LineIterator(sharedReader);
        LineIterator secondaryIterator = new LineIterator(sharedReader);

        // Closing secondaryIterator also closes the shared reader,
        // leaving primaryIterator in a broken state.
        LineIterator.closeQuietly(secondaryIterator);

        try {
            primaryIterator.nextLine();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.apache.commons.io.LineIterator", e);
        }
    }
}
