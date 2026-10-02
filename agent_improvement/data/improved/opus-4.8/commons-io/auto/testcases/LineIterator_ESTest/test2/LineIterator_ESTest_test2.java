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
     * A LineIterator over an empty Reader should report that it has no lines.
     * Calling hasNext() repeatedly must keep returning false without
     * advancing or changing state.
     */
    @Test(timeout = 4000)
    public void hasNext_onEmptyReader_alwaysReturnsFalse() throws Throwable {
        StringReader emptyReader = new StringReader("");
        LineIterator lineIterator = new LineIterator(emptyReader);

        assertFalse("Empty input has no lines", lineIterator.hasNext());
        assertFalse("Repeated hasNext() stays false", lineIterator.hasNext());
    }
}
