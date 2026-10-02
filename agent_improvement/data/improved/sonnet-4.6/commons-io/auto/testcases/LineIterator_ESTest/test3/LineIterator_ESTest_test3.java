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

    @Test(timeout = 4000)
    public void test3_hasNextIsIdempotentBeforeLineIsConsumed() throws Throwable {
        // A single-line reader: hasNext() should return true on repeated calls
        // before next() is invoked, because the cached line is not cleared.
        StringReader singleLineReader = new StringReader("Jo\"1* 1@z}r@|`5#o");
        LineIterator iterator = new LineIterator(singleLineReader);

        iterator.hasNext(); // first call reads and caches the line
        boolean stillHasNext = iterator.hasNext(); // second call uses the cached line

        assertTrue(stillHasNext);
    }
}
