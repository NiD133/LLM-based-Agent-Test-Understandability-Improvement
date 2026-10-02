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
     * confirming idempotency: calling it multiple times does not change the result.
     */
    @Test(timeout = 4000)
    public void test_hasNext_returnsFalse_whenReaderIsEmpty() throws Throwable {
        StringReader emptyReader = new StringReader("");
        LineIterator iterator = new LineIterator(emptyReader);

        boolean hasNextOnFirstCall = iterator.hasNext();
        assertFalse("Empty reader should have no next element on first call", hasNextOnFirstCall);

        boolean hasNextOnSecondCall = iterator.hasNext();
        assertFalse("Empty reader should still have no next element on repeated call", hasNextOnSecondCall);
    }
}
