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
     * Verifies that hasNext() consistently returns false for an empty reader,
     * even when called multiple times (idempotent behavior).
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // An empty string yields a reader with no lines
        StringReader emptyReader = new StringReader("");
        LineIterator lineIterator = new LineIterator(emptyReader);

        // First call: no lines available
        boolean hasNextOnFirstCall = lineIterator.hasNext();
        assertFalse(hasNextOnFirstCall);

        // Second call: still no lines — hasNext() must be idempotent on empty input
        boolean hasNextOnSecondCall = lineIterator.hasNext();
        assertFalse(hasNextOnSecondCall);
    }
}
