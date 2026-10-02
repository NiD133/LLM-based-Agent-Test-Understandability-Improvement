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
public class LineIterator_ESTest_test1 extends LineIterator_ESTest_scaffolding {

    /**
     * Verifies that calling nextLine() on a LineIterator backed by an empty reader
     * throws NoSuchElementException, because there are no lines to iterate over.
     */
    @Test(timeout = 4000)
    public void test_nextLine_onEmptyReader_throwsNoSuchElementException() throws Throwable {
        StringReader emptyReader = new StringReader("");
        LineIterator lineIterator = new LineIterator(emptyReader);

        try {
            lineIterator.nextLine();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException("org.apache.commons.io.LineIterator", e);
        }
    }
}
