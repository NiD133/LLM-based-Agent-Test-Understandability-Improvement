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
public class LineIterator_ESTest_test2 extends LineIterator_ESTest_scaffolding {

    /**
     * Verifies that a LineIterator over an empty input reports no available
     * lines, and that repeated calls to hasNext() remain consistent (both
     * return false) without advancing or throwing.
     */
    @Test(timeout = 4000)
    public void hasNextReturnsFalseForEmptyInput() throws Throwable {
        StringReader emptyReader = new StringReader("");
        LineIterator lineIterator = new LineIterator(emptyReader);

        // No lines are available in empty input.
        assertFalse(lineIterator.hasNext());

        // Calling hasNext() again yields the same result.
        assertFalse(lineIterator.hasNext());
    }
}
