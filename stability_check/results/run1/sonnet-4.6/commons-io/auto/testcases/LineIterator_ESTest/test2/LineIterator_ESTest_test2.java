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
     * confirming the method is idempotent when there are no lines to iterate.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        StringReader emptyReader = new StringReader("");
        LineIterator iterator = new LineIterator(emptyReader);

        boolean hasNextFirstCall = iterator.hasNext();
        assertFalse(hasNextFirstCall);

        boolean hasNextSecondCall = iterator.hasNext();
        assertFalse(hasNextSecondCall);
    }
}
